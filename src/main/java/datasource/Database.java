package datasource;

import commands.parameters.AddTaskParameter;

import java.sql.SQLException;

public interface Database {
    void save(AddTaskParameter task) throws SQLException;
}
