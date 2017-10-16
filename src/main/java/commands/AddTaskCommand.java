package commands;

import commands.parameters.AddTaskParameter;
import datasource.Database;

import java.sql.SQLException;

public class AddTaskCommand extends Command<AddTaskParameter> {

    public AddTaskCommand(AddTaskParameter parameter) {
        super(parameter);
    }

    @Override
    void executeParameters(Database database) {
        try {
            database.save(parameter);
        } catch (SQLException e) {
            throw new RuntimeException("SQL execution failed", e);
        }
    }
}
