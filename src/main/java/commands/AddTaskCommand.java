package commands;

import datasource.Database;
import dates.DateParser;

import java.sql.SQLException;
import java.sql.Timestamp;

public class AddTaskCommand extends Command {

    private final String description;
    private final Timestamp deadline;

    private AddTaskCommand(String description, Timestamp deadline) {
        this.description = description;
        this.deadline = deadline;
        determineState();
    }

    public static AddTaskCommand from(String[] descriptionArray, String daysToDeadline) {
        String description = null;
        if (descriptionArray != null) {
            description = String.join(" ", descriptionArray);
            logger.trace("Adding description: {}", description);
        }

        Timestamp deadline = null;
        if (daysToDeadline != null) {
            deadline = DateParser.parseDate(daysToDeadline);
        }

        return new AddTaskCommand(description, deadline);
    }

    /**
     * If description is present, we can save the task in the database.
     *
     * If description is missing, it doesn't mean the parameters are
     * invalid. It could be that the user did not want to add a task.
     *
     * However, if description is null and deadline is not, it is clear
     * user wanted to do add a task with a deadline, but the parameters
     * supplied are insufficient to do so.
     *
     * @return state indicating if command is valid and can be acted upon.
     */
    @Override
    protected void determineState() {
        if (description != null) {
            this.state = Command.State.VALID;
        } else if (description == null && deadline != null) {
            this.errorMessage = "Description must be provided when adding a task";
            this.state = Command.State.INVALID;
        } else {
            this.state = Command.State.EMPTY;
        }
    }

    @Override
    void executeCommand(Database database) {
        try {
            database.save(this);
        } catch (SQLException e) {
            throw new RuntimeException("SQL execution failed", e);
        }
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDeadline() {
        return deadline;
    }
}
