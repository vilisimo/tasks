package datasource;

import configuration.JdbcConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    public Database(JdbcConfiguration config) {
        try {
            connection = DriverManager.getConnection(config.url, config.username, config.password);
            System.out.println("Established a database connection!");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close database connection", e);
            }
        }
    }
}
