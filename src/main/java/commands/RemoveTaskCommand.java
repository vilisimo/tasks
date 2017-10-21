package commands;

import commands.parameters.RemoveTaskParameter;
import datasource.Database;

import java.sql.SQLException;

public class RemoveTaskCommand extends Command<RemoveTaskParameter> {

    public RemoveTaskCommand(RemoveTaskParameter parameter) {
        super(parameter);
    }

    @Override
    void executeParameters(Database database) {
        try {
            database.delete(parameter);
        } catch (SQLException e){
            throw new RuntimeException("Deletion of a task failed", e);
        }
    }
}
