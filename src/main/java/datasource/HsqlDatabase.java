package datasource;

import commands.AddTaskCommand;
import commands.RemoveTaskCommand;
import configuration.JdbcConfiguration;
import entities.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.jdbc.JDBCPool;
import printing.Printer;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides HSQLDB-specific implementation of the
 * database operations.
 */
class HsqlDatabase implements Database {

    private final static Logger logger = LogManager.getLogger();

    private JDBCPool connectionPool;

    HsqlDatabase(JdbcConfiguration config) {
        connectionPool = new JDBCPool(10);
        connectionPool.setURL(config.url);
        connectionPool.setUser(config.username);
        connectionPool.setPassword(config.password);
    }

    @Override
    public long save(AddTaskCommand task) throws SQLException {
        logger.trace("Attempting to save a task");

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(Statements.insert(), Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, task.getDescription());
            statement.setTimestamp(2, task.getDeadline());
            statement.setString(3, task.getCategory());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                long taskId = resultSet.getLong(1);

                logger.info("Successfully committed a task(id={}) to the database", taskId);

                return taskId;
            }
        }
    }

    @Override
    public List<Task> getAll() throws SQLException {
        logger.trace("Attempting to retrieve all tasks");

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(Statements.select())) {

            try (ResultSet rs = statement.executeQuery()) {
                List<Task> tasks = createTasks(rs);

                logger.info("Successfully retrieved all tasks");

                return tasks;
            }
        }
    }

    @Override
    public int delete(RemoveTaskCommand task) throws SQLException {
        logger.trace("Attempting to remove a task [id={}]", task.getTaskId());

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

            logger.info("Successfully removed all tasks");
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
                List<Task> tasks = createTasks(rs);

                logger.info("Successfully filtered tasks on deadline [={}]", deadline);

                return tasks;
            }
        }
    }

    @Override
    public List<Task> filter(String category) throws SQLException {
        logger.trace("Attempting to retrieve tasks filtered category(={})", category);

        String query = Statements.filter(category);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (!query.contains("NULL")) {
                statement.setString(1, category);
            }

            try (ResultSet rs = statement.executeQuery()) {
                List<Task> tasks = createTasks(rs);

                logger.trace("Successfully filtered tasks on category [={}]", category);

                return tasks;
            }
        }
    }

    private List<Task> createTasks(ResultSet rs) throws SQLException {
        List<Task> tasks = new ArrayList<>();

        while (rs.next()) {
            int taskId = rs.getInt(1);
            logger.trace("Creating a task representation (id=" + taskId + ")");

            String description = rs.getString(2);
            Timestamp createdDate = rs.getTimestamp(3);
            Timestamp endDate = rs.getTimestamp(4);
            String category = rs.getString(5);
            Instant created = createdDate == null ? null : createdDate.toInstant();
            Instant deadline = endDate == null ? null : endDate.toInstant();

            Task task = new Task(taskId, description, category, created, deadline);
            tasks.add(task);
        }

        return tasks;
    }
}
