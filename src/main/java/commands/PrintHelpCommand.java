package commands;

import datasource.Database;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class PrintHelpCommand extends Command {

    private final boolean executable;
    private final Options options;

    private PrintHelpCommand(boolean executable, Options options) {
        this.executable = executable;
        this.options = options;
        determineState();
    }

    public static PrintHelpCommand from(boolean executable, Options options) {
        return new PrintHelpCommand(executable, options);
    }

    @Override
    protected void determineState() {
        if (executable) {
            this.state = State.VALID;
        } else {
            this.state = State.EMPTY;
        }
    }

    @Override
    void executeCommand(Database database) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("tasks", options);
    }
}
