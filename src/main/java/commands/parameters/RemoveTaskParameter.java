package commands.parameters;

public class RemoveTaskParameter extends Parameter {

    private final Integer taskId;

    public RemoveTaskParameter(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public State determineState() {
        if (taskId == null) {
            return State.EMPTY;
        } else if (taskId >= 0) {
            return State.VALID;
        } else {
            this.errorMessage = "Task id cannot be negative";
            return State.INVALID;
        }
    }

    public Integer getTaskId() {
        return taskId;
    }
}
