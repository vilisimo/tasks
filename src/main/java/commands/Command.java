package commands;

import configuration.JdbcConfiguration;

import java.sql.SQLException;

public interface Command {
    void execute(JdbcConfiguration configuration) throws SQLException;
}
