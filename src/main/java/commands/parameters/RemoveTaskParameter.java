package commands.parameters;

public class RemoveTaskParameter extends Parameter {

    private final int taskId;

    public RemoveTaskParameter(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public State determineState() {
        if (taskId >= 0) {
            return State.VALID;
        } else {
            this.errorMessage = "Task id cannot be negative";
            return State.INVALID;
        }
    }

    public int getTaskId() {
        return taskId;
    }
}
