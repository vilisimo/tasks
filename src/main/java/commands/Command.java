package commands;

import coloring.Printer;
import datasource.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.Objects.requireNonNull;

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
            default:
                break;
        }
    }

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
