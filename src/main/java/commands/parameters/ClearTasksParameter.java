package commands.parameters;

public class ClearTasksParameter extends Parameter {

    private boolean empty;

    public ClearTasksParameter(boolean empty) {
        this.empty = empty;
    }

    @Override
    public State determineState() {
        if (empty) {
            return State.EMPTY;
        } else {
            return State.VALID;
        }
    }
}
