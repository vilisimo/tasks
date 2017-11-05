package datasource;

import commands.AddTaskCommand;
import commands.RemoveTaskCommand;
import entities.Task;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface Database {
    void save(AddTaskCommand task) throws SQLException;
    List<Task> getAll() throws SQLException;
    int delete(RemoveTaskCommand task) throws SQLException;
    void clear() throws SQLException;
    List<Task> filter(Timestamp deadline) throws SQLException;
    List<Task> filter(String category) throws SQLException;
}
