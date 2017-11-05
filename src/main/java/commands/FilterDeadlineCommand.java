package commands;

import datasource.Database;
import entities.Task;
import printing.Printer;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class FilterDeadlineCommand extends FilterTasksCommand {

    private Timestamp deadline;

    FilterDeadlineCommand(boolean executable, Timestamp filter) {
        this.executable = executable;
        this.deadline = filter;
        super.determineState();
    }

    @Override
    void executeCommand(Database database) {
        List<Task> tasks;
        try {
            tasks = database.filter(deadline);
        } catch (SQLException e) {
            throw new RuntimeException("Retrieval of tasks has failed", e);
        }

        Printer.printTasks(tasks);
    }
}
