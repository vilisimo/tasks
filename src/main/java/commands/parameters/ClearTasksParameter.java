package commands.parameters;

public class ClearTasksParameter extends Parameter {

    private boolean executable;

    public ClearTasksParameter(boolean executable) {
        this.executable = executable;
    }

    @Override
    public State determineState() {
        if (executable) {
            return State.VALID;
        } else {
            return State.EMPTY;
        }
    }
}
