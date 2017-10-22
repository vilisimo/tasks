package commands;

import commands.parameters.ClearTasksParameter;
import datasource.Database;

import java.sql.SQLException;

public class ClearTasksCommand extends Command<ClearTasksParameter> {

    public ClearTasksCommand(ClearTasksParameter parameter) {
        super(parameter);
    }

    @Override
    void executeParameters(Database database) {
        try {
            database.clear(parameter);
        } catch (SQLException e) {
            throw new RuntimeException("Clearing of the tasks failed", e);
        }
    }
}
