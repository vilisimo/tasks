package datasource;

import commands.parameters.AddTaskParameter;
import commands.parameters.ShowTasksParameter;
import configuration.JdbcConfiguration;
import entities.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.jdbc.JDBCPool;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class HsqlDatabase implements Database {

    private final static Logger logger = LogManager.getLogger();

    private static final String INSERT = "INSERT INTO TASKS(description, deadline) VALUES (?, ?)";
    private static final String SELECT = "SELECT * FROM TASKS";

    private JDBCPool connectionPool;

    HsqlDatabase(JdbcConfiguration config) {
        connectionPool = new JDBCPool(10);
        connectionPool.setURL(config.url);
        connectionPool.setUser(config.username);
        connectionPool.setPassword(config.password);
        logger.trace("Created a connection pool (url={})", config.url);
    }

    @Override
    public void save(AddTaskParameter task) throws SQLException {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, task.getDescription());
            statement.setTimestamp(2, task.getDeadline());
            statement.executeUpdate();
            logger.trace("Successfully saved a task");

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                logger.info("Committed task to the database, id={}", resultSet.getLong(1));
            }
        }
    }

    @Override
    public List<Task> getAll(ShowTasksParameter parameter) throws SQLException {
        logger.trace("Attempting to retrieve all tasks");
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT)) {

            try (ResultSet rs = statement.executeQuery()) {
                logger.trace("Retrieved a result representing all tasks");

                List<Task> tasks = new ArrayList<>();
                while (rs.next()) {
                    int taskId = rs.getInt(1);
                    logger.trace("Processing task (id=" + taskId + ")");
                    String description = rs.getString(2);
                    Timestamp createdDate = rs.getTimestamp(3);
                    Timestamp endDate = rs.getTimestamp(4); // 3rd column is "created"
                    Instant created = createdDate == null ? null : createdDate.toInstant();
                    Instant deadline = endDate == null ? null : endDate.toInstant();

                    Task task = new Task(taskId, description, created, deadline);
                    tasks.add(task);
                }

                logger.trace("Successfully retrieved all tasks");

                return tasks;
            }
        }
    }
}
