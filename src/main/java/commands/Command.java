package commands;

import datasource.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import printing.Printer;

import static java.util.Objects.requireNonNull;

/**
 * Base command that all commands should inherit from.
 *
 * The class encapsulates a standard way of execution.
 * To affect the outcome, subclasses should override
 * the abstract methods with appropriate behavior.
 */
public abstract class Command {

    static final Logger logger = LogManager.getLogger();

    String errorMessage;
    State state;

    public final void execute(Database database) {
        requireNonNull(state, this.getClass().getSimpleName() + "'s state cannot be null");

        logger.trace("Executing {} (state={})", this.getClass().getSimpleName(), state.toString());

        switch (state) {
            case VALID:
                executeCommand(database);
                break;
            case INVALID:
                showError();
                break;
            default: // EMPTY state: do nothing
                break;
        }
    }

    protected abstract void determineState();

    abstract void executeCommand(Database database);

    private void showError() {
        Printer.error(errorMessage);
    }

    State getState() {
        return state;
    }

    public enum State {
        VALID, INVALID, EMPTY
    }
}
