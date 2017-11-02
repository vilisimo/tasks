package commands;

import printing.Printer;
import datasource.Database;
import dates.DateParser;
import entities.Task;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class FilterTasksCommand extends Command {

    private Timestamp deadline;

    private boolean executable;

    private FilterTasksCommand(boolean executable, String filter) {
        this.executable = executable;
        this.deadline = Optional.ofNullable(filter).map(this::parseDeadline).orElse(null);
        determineState();
    }

    private Timestamp parseDeadline(String daysToDeadline) {
        if (!daysToDeadline.equalsIgnoreCase("none")) {
            return DateParser.parseDate(daysToDeadline);
        }

        return null;
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
            tasks = database.filter(deadline);
        } catch (SQLException e) {
            throw new RuntimeException("Retrieval of tasks has failed", e);
        }

        Printer.printTasks(tasks);
    }

    public static FilterTasksCommand from(String optionValue) {
        logger.trace("Creating inner class of {}: {}", FilterTasksCommand.class.getSimpleName(),
                FilterTasksCommand.class.getSimpleName());

        return new FilterTasksCommand(optionValue != null, optionValue);
    }
}
