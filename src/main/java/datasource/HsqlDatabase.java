package datasource;

import printing.Printer;
import commands.AddTaskCommand;
import commands.RemoveTaskCommand;
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

    private JDBCPool connectionPool;

    HsqlDatabase(JdbcConfiguration config) {
        connectionPool = new JDBCPool(10);
        connectionPool.setURL(config.url);
        connectionPool.setUser(config.username);
        connectionPool.setPassword(config.password);
        logger.trace("Created a connection pool (url={})", config.url);
    }

    @Override
    public void save(AddTaskCommand task) throws SQLException {
        logger.trace("Attempting to save a task");

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(Statements.insert(), Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, task.getDescription());
            statement.setTimestamp(2, task.getDeadline());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                long taskId = resultSet.getLong(1);
                Printer.success("Successfully saved a task with id=" + taskId + ".");

                logger.info("Committed task(id={}) to the database", taskId);
            }
        }
    }

    @Override
    public List<Task> getAll() throws SQLException {
        logger.trace("Attempting to retrieve all tasks");
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(Statements.select())) {

            try (ResultSet rs = statement.executeQuery()) {
                logger.trace("Retrieved a result representing all tasks");
                List<Task> tasks = createTasks(rs);
                logger.trace("Successfully retrieved all tasks");

                return tasks;
            }
        }
    }

    @Override
    public int delete(RemoveTaskCommand task) throws SQLException {
        logger.trace("Attempting to remove a task (id={})", task.getTaskId());

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(Statements.delete())) {

            statement.setInt(1, task.getTaskId());
            int affectedRows = statement.executeUpdate();

            logger.trace("Successfully deleted a row (id={})", task.getTaskId());

            return affectedRows;
        }
    }

    @Override
    public void clear() throws SQLException {
        logger.trace("Attempting to clear all tasks");

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(Statements.truncate())) {

            statement.executeUpdate();

            logger.trace("Successfully removed all tasks");
        }
    }

    @Override
    public List<Task> filter(Timestamp deadline) throws SQLException {
        logger.trace("Attempting to retrieve tasks filtered deadline(={})", deadline);

        String query = Statements.filter(deadline);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (!query.contains("NULL")) {
                statement.setTimestamp(1, deadline);
            }

            try (ResultSet rs = statement.executeQuery()) {
                logger.trace("Retrieved a result representing all tasks");
                List<Task> tasks = createTasks(rs);
                logger.trace("Successfully filtered and retrieved the tasks");

                return tasks;
            }
        }
    }

    private List<Task> createTasks(ResultSet rs) throws SQLException {
        List<Task> tasks = new ArrayList<>();

        while (rs.next()) {
            int taskId = rs.getInt(1);
            logger.trace("Processing task (id=" + taskId + ")");
            String description = rs.getString(2);
            Timestamp createdDate = rs.getTimestamp(3);
            Timestamp endDate = rs.getTimestamp(4);
            Instant created = createdDate == null ? null : createdDate.toInstant();
            Instant deadline = endDate == null ? null : endDate.toInstant();

            Task task = new Task(taskId, description, created, deadline);
            tasks.add(task);
        }

        return tasks;
    }
}
