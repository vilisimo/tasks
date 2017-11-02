package commands;

import datasource.Database;
import entities.Task;
import printing.Printer;

import java.sql.SQLException;
import java.util.List;

public class ShowAllTasksCommand extends Command {

    private boolean executable;

    public ShowAllTasksCommand(boolean executable) {
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
        List<Task> tasks;
        try {
            tasks = database.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Retrieval of tasks has failed", e);
        }

        Printer.printTasks(tasks);
    }
}
