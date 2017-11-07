package commands;

import datasource.Database;
import utils.Chronos;
import utils.Strings;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Command that handles adding tasks to the data storage.
 */
public class AddTaskCommand extends Command {

    private final String description;
    private final String category;
    private final Timestamp deadline;

    private AddTaskCommand(String description, String category, Timestamp deadline) {
        this.description = description;
        this.deadline = deadline;
        this.category = category;
        determineState();
    }

    public static AddTaskCommand from(String[] descriptionArray, String daysToDeadline, String[] categoryArray) {
        String description = Strings.joinStrings(descriptionArray).orElse(null);
        Timestamp deadline = Chronos.convertDaysToTimestamp(daysToDeadline).orElse(null);
        String category = Strings.joinStrings(categoryArray).orElse("");

        return new AddTaskCommand(description, category, deadline);
    }

    /**
     * If description is present, we can save the task in the database.
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
        } else if (description == null && (deadline != null || !category.equals("") )) {
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
            throw new RuntimeException("SQL execution has failed", e);
        }
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public String getCategory() {
        return category;
    }
}
