package datasource;

import commands.AddTaskCommand;
import commands.parameters.ClearTasksParameter;
import commands.parameters.RemoveTaskParameter;
import entities.Task;

import java.sql.SQLException;
import java.util.List;

public interface Database {
    void save(AddTaskCommand task) throws SQLException;
    List<Task> getAll() throws SQLException;
    int delete(RemoveTaskParameter task) throws SQLException;
    void clear(ClearTasksParameter parameter) throws SQLException;
}
