package commands;

import coloring.Printer;
import commands.parameters.ShowTasksParameter;
import datasource.Database;
import entities.Task;
import utils.TimeUtils;

import java.sql.SQLException;
import java.util.List;

public class ShowTasksCommand extends Command<ShowTasksParameter> {

    public ShowTasksCommand(ShowTasksParameter parameter) {
        super(parameter);
    }

    @Override
    void executeParameters(Database database) {
        try {
            List<Task> tasks = database.getAll(parameter);

            tasks.forEach(task -> {
                Printer.success("Task " + task.getId() + ":"
                        + "\n" + task.getDescription()
                        + "\nCreated: " + TimeUtils.instantLocalDate(task.getCreated())
                        + "\nDeadline: " + TimeUtils.instantLocalDate(task.getDeadline())
                        + "\n");
            });
        } catch (SQLException e) {
            throw new RuntimeException("Retrieval of tasks has failed", e);
        }
    }
}
