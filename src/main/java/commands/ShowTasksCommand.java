package commands;

import coloring.Printer;
import datasource.Database;
import entities.Task;
import utils.TimeUtils;

import java.sql.SQLException;
import java.util.List;

public class ShowTasksCommand extends Command {

    public ShowTasksCommand(boolean executable) {
        if (executable) {
            state = State.VALID;
        } else {
            state = State.EMPTY;
        }
    }

    @Override
    void executeParameters(Database database) {
        List<Task> tasks;
        try {
            tasks = database.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Retrieval of tasks has failed", e);
        }
        
        tasks.forEach(task -> Printer.success(
                "Task " + task.getId() + ":"
                + "\n" + task.getDescription()
                + "\nCreated: " + TimeUtils.instantLocalDate(task.getCreated())
                + "\nDeadline: " + TimeUtils.instantLocalDate(task.getDeadline())
                + "\n")
        );
    }
}
