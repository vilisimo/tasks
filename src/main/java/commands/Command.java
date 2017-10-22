package commands;

import coloring.Printer;
import commands.parameters.Parameter;
import datasource.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.Objects.requireNonNull;

public abstract class Command<T extends Parameter> {

    static final Logger logger = LogManager.getLogger();

    // TODO: temporary for tests to compile. Remove later.
    final T parameter;

    protected String errorMessage;
    protected State state;

    Command(T parameter) {
        this.parameter = parameter;
        this.state = State.EMPTY; // TODO: temporary to allow manual testing. Remove later.
    }

    // TODO: temporary for tests to compile. Remove later.
    protected Command() {
        parameter = null;
    }

    public final void execute(Database database) {
        requireNonNull(state, this.getClass().getSimpleName() + "'s state cannot be null");

        logger.trace("Executing {} (state={})", this.getClass().getSimpleName(), state.toString());

        switch (state) {
            case VALID:
                executeParameters(database);
                break;
            case INVALID:
                showError();
                break;
            default:
                break;
        }
    }

    // TODO: remove method after removal of parameters.
    abstract void executeParameters(Database database);

    private void showError() {
        Printer.error(errorMessage);
    }

    public State getState() {
        return state;
    }

    public enum State {
        VALID, INVALID, EMPTY
    }
}
