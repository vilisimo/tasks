package commands;

import datasource.Database;

import java.sql.SQLException;

public class ClearTasksCommand extends Command {

    private boolean executable;

    public ClearTasksCommand(boolean executable) {
        this.executable = executable;
        determineState();
    }

    @Override
    protected void determineState() {
        if (executable) {
            this.state = State.VALID;
        } else {
            this.state = State.EMPTY;
        }
    }

    @Override
    void executeCommand(Database database) {
        try {
            database.clear();
        } catch (SQLException e) {
            throw new RuntimeException("Clearing of the tasks failed", e);
        }
    }
}
