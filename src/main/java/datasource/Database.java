package datasource;

import configuration.JdbcConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Database {

    private final static Logger logger = LogManager.getLogger();

    private Connection connection;

    public Database(JdbcConfiguration config) {
        try {
            connection = DriverManager.getConnection(config.url, config.username, config.password);
            logger.info("Established a database connection (url={})", config.url);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.trace("Successfully closed database connection");
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close database connection", e);
            }
        }
    }
}
