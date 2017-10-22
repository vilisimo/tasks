package commands;

import datasource.Database;

import java.sql.SQLException;

public class ClearTasksCommand extends Command {

    public ClearTasksCommand(boolean executable) {
        if (executable) {
            state = State.VALID;
        } else {
            state = State.EMPTY;
        }
    }

    @Override
    void executeParameters(Database database) {
        try {
            database.clear();
        } catch (SQLException e) {
            throw new RuntimeException("Clearing of the tasks failed", e);
        }
    }
}
