package commands;

import datasource.Database;
import entities.Task;
import printing.Printer;

import java.sql.SQLException;
import java.util.List;

/**
 * Command that allows filtering tasks on categories.
 */
class FilterCategoryCommand extends FilterTasksCommand {

    private final String category;

    FilterCategoryCommand(boolean executable, String category) {
        this.executable = executable;
        this.category = category;
        super.determineState();
    }

    @Override
    void executeCommand(Database database) {
        List<Task> tasks;
        try {
            tasks = database.filter(category);
        } catch (SQLException e) {
            throw new RuntimeException("Filtering of tasks on category [="  + category + "] has failed");
        }

        Printer.printTasks(tasks);
    }
}
