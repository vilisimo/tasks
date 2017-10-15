package commands.parameters;

/**
 * The class that contains parameters used for executing the commands.
 *
 * Every implementation of Parameter class can have three states:
 * - VALID: it can be acted upon and passed to a command for execution
 * - INVALID: it cannot be acted upon and error message should be shown
 * - EMPTY: it can b acted upon, but it will have no effect
 */
public abstract class Parameter {

    String errorMessage;

    public abstract State determineState();

    public String getErrorMessage() {
        return errorMessage;
    }

    public enum State {
        VALID, INVALID, EMPTY
    }
}
