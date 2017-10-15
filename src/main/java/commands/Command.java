package commands;

import coloring.Printer;
import commands.parameters.Parameter;
import datasource.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Command<T extends Parameter> {

    static final Logger logger = LogManager.getLogger();

    final T parameter;

    Command(T parameter) {
        this.parameter = parameter;
    }

    public final void execute(Database database) {
        logger.trace("Executing {} on {}", this.getClass().getSimpleName(), parameter.getClass().getSimpleName());

        Parameter.State state = parameter.determineState();
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

    abstract void executeParameters(Database database);

    private void showError() {
        Printer.error(parameter.getErrorMessage());
    }
}
