package commands;

import coloring.Printer;
import datasource.Database;
import dates.DateParser;
import entities.Task;
import utils.Chronos;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public abstract class ShowTasksCommand extends Command {

    private boolean executable;

    private ShowTasksCommand(boolean executable) {
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

    public static ShowTasksCommand from(boolean executable, String optionValue) {
        if (optionValue == null) {
            logger.trace("Creating inner class of {}: {}", ShowTasksCommand.class.getSimpleName(),
                    ShowAllTasksCommand.class.getSimpleName());
            return new ShowAllTasksCommand(executable);
        } else {
            logger.trace("Creating inner class of {}: {}", ShowTasksCommand.class.getSimpleName(),
                    ShowTimeFilteredTasks.class.getSimpleName());
            return new ShowTimeFilteredTasks(executable, optionValue);
        }
    }

    private static class ShowAllTasksCommand extends ShowTasksCommand {

        private ShowAllTasksCommand(boolean executable) {
            super(executable);
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

    private static class ShowTimeFilteredTasks extends ShowTasksCommand {

        private Timestamp deadline;

        private ShowTimeFilteredTasks(boolean executable, String daysToDeadline) {
            super(executable);
            this.deadline = parseDeadline(daysToDeadline);
        }

        @Override
        void executeCommand(Database database) {
            List<Task> tasks;
            try {
                tasks = database.filter(deadline);
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

        private Timestamp parseDeadline(String daysToDeadline) {
            if (!daysToDeadline.equalsIgnoreCase("none")) {
                return DateParser.parseDate(daysToDeadline);
            }

            return null;
        }
    }
}
