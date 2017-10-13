package datasource;

import configuration.JdbcConfiguration;
import entities.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.jdbc.JDBCPool;

import java.sql.*;

class HsqlDatabase implements Database {

    private final static Logger logger = LogManager.getLogger();

    private static final String INSERT = "INSERT INTO TASKS(description, deadline) VALUES (?, ?)";

    private JDBCPool connectionPool;

    HsqlDatabase(JdbcConfiguration config) {
        connectionPool = new JDBCPool(10);
        connectionPool.setURL(config.url);
        connectionPool.setUser(config.username);
        connectionPool.setPassword(config.password);
        logger.trace("Created a connection pool (url={})", config.url);
    }

    @Override
    public void save(Task task) throws SQLException {
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
}
