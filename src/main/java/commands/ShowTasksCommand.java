package commands;

import coloring.Printer;
import datasource.Database;
import entities.Task;
import utils.Chronos;

import java.sql.SQLException;
import java.util.List;

public class ShowTasksCommand extends Command {

    private boolean executable;

    public ShowTasksCommand(boolean executable) {
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
        
        tasks.forEach(task -> Printer.success(
                "Task " + task.getId() + ":"
                + "\n" + task.getDescription()
                + "\nCreated: " + Chronos.instantLocalDate(task.getCreated())
                + "\nDeadline: " + Chronos.instantLocalDate(task.getDeadline())
                + "\n")
        );
    }
}
