package cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cli.OptionNames.*;

public final class CliOptions {

    private CliOptions() {
        throw new AssertionError("The class should not be instantiated");
    }

    private static final Logger logger = LogManager.getLogger();

    public static Options createOptions() {
        Options options = new Options();
        options.addOption(createAddTaskCommand());
        options.addOption(createDeadline());
        options.addOption(createShowCommand());
        options.addOption(createRemoveCommand());

        logger.info("Added command line options");

        return options;
    }

    private static Option createAddTaskCommand() {
        return Option.builder(ADD)
                .desc("Adds a new task")
                .argName("Add task")
                .hasArgs()
                .build();
    }

    private static Option createDeadline() {
        return Option.builder(DEADLINE)
                .desc("Add deadline for the task")
                .argName("Deadline")
                .hasArg()
                .build();
    }


    private static Option createShowCommand() {
        return Option.builder(SHOW)
                .desc("Shows currently saved tasks")
                .argName("Show tasks")
                .build();
    }

    private static Option createRemoveCommand() {
        return Option.builder(REMOVE)
                .desc("Removes a task by its id")
                .argName("Remove task")
                .hasArg()
                .build();
    }
}
