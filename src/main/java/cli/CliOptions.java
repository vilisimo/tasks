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
        options.addOption(createAddTaskOption());
        options.addOption(createDeadlineOption());
        options.addOption(createFilterOption());
        options.addOption(createRemoveOption());
        options.addOption(createClearOption());
        options.addOption(createCategoryOption());

        logger.info("Added command line options");

        return options;
    }

    private static Option createAddTaskOption() {
        return Option.builder(ADD.shortOpt())
                .longOpt(ADD.longOpt())
                .desc("Adds a new task")
                .argName("Add task")
                .hasArgs()
                .build();
    }

    private static Option createDeadlineOption() {
        return Option.builder(DEADLINE.shortOpt())
                .longOpt(DEADLINE.longOpt())
                .desc("Add deadline for the task")
                .argName("Deadline")
                .hasArg()
                .build();
    }

    private static Option createCategoryOption() {
        return Option.builder(CATEGORY.shortOpt())
                .longOpt(CATEGORY.longOpt())
                .desc("Add category for the task")
                .argName("Category")
                .hasArgs()
                .build();
    }

    private static Option createFilterOption() {
        return Option.builder(FILTER.shortOpt())
                .longOpt(FILTER.longOpt())
                .desc("Filters saved tasks on given deadline")
                .argName("Filter tasks")
                .hasArg()
                .build();
    }

    private static Option createRemoveOption() {
        return Option.builder(REMOVE.shortOpt())
                .longOpt(REMOVE.longOpt())
                .desc("Removes a task by its id")
                .argName("Remove task")
                .hasArg()
                .build();
    }

    private static Option createClearOption() {
        return Option.builder(CLEAR.shortOpt())
                .longOpt(CLEAR.longOpt())
                .desc("Clears (removes) all tasks")
                .argName("Clear tasks")
                .build();
    }
}
