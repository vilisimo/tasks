package datasource;

import entities.Task;

import java.sql.SQLException;

public interface Database {
    void save(Task task) throws SQLException;
}
