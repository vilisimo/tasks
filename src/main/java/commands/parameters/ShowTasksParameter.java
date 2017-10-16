package commands.parameters;

public class ShowTasksParameter extends Parameter {

    private boolean empty;

    public ShowTasksParameter(boolean empty) {
        this.empty = empty;
    }

    @Override
    public State determineState() {
        if (!empty) {
            return State.VALID;
        } else {
            return State.EMPTY;
        }
    }
}
