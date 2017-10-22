package datasource;

import commands.parameters.AddTaskParameter;
import commands.parameters.ClearTasksParameter;
import commands.parameters.RemoveTaskParameter;
import commands.parameters.ShowTasksParameter;
import entities.Task;

import java.sql.SQLException;
import java.util.List;

public interface Database {
    void save(AddTaskParameter task) throws SQLException;
    List<Task> getAll(ShowTasksParameter parameter) throws SQLException;
    int delete(RemoveTaskParameter task) throws SQLException;
    void clear(ClearTasksParameter parameter) throws SQLException;
}
