package commands;

import datasource.Database;

import java.sql.SQLException;

/**
 * Command responsible for removing individual tasks
 * from data storage.
 */
public class RemoveTaskCommand extends Command {

    private final Integer taskId;

    private RemoveTaskCommand(Integer taskId) {
        this.taskId = taskId;
        determineState();
    }

    public static RemoveTaskCommand from(String optionValue) {
        if (optionValue != null) {
            try {
                return new RemoveTaskCommand(Integer.parseInt(optionValue));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("\"" + optionValue + "\" is not a valid id (=integer)");
            }
        } else {
            return new RemoveTaskCommand(null);
        }
    }

    @Override
    protected void determineState() {
        if (taskId == null) {
            this.state = Command.State.EMPTY;
        } else if (taskId >= 0) {
            this.state = Command.State.VALID;
        } else {
            this.errorMessage = "Task id cannot be negative [input=" + taskId + "]";
            this.state = Command.State.INVALID;
        }
    }

    @Override
    void executeCommand(Database database) {
        try {
            database.delete(this);
        } catch (SQLException e){
            throw new RuntimeException("Deletion of a task failed", e);
        }
    }

    public Integer getTaskId() {
        return taskId;
    }
}
