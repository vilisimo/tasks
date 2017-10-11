package datasource;

import configuration.JdbcConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.jdbc.JDBCPool;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private final static Logger logger = LogManager.getLogger();

    private JDBCPool connectionPool;

    public Database(JdbcConfiguration config) {
        connectionPool = new JDBCPool(10);
        connectionPool.setURL(config.url);
        connectionPool.setUser(config.username);
        connectionPool.setPassword(config.password);
        logger.trace("Created a connection pool (url={})", config.url);
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

}
