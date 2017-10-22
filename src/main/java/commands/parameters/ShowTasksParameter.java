package commands.parameters;

public class ShowTasksParameter extends Parameter {

    private boolean executable;

    public ShowTasksParameter(boolean executable) {
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
