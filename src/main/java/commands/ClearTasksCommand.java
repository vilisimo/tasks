package commands;

import datasource.Database;
import printing.Printer;

import java.sql.SQLException;

/**
 * Command that handles removing all the taks from data
 * storage.
 */
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
            Printer.success("Tasks have been cleared");
        } catch (SQLException e) {
            throw new RuntimeException("Clearing of the tasks has failed", e);
        }
    }
}
