package commands;

import datasource.Database;

import java.sql.SQLException;

public class RemoveTaskCommand extends Command {

    private final Integer taskId;

    public RemoveTaskCommand(Integer taskId) {
        this.taskId = taskId;
        determineState();
    }

    private void determineState() {
        if (taskId == null) {
            this.state = Command.State.EMPTY;
        } else if (taskId >= 0) {
            this.state = Command.State.VALID;
        } else {
            this.errorMessage = "Task id cannot be negative";
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
