import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CliOptions {

    private static final Logger logger = LogManager.getLogger();

    public static Options createOptions() {
        Options options = new Options();
        options.addOption(createAddTask());
        options.addOption(createDeadline());
        logger.info("Added command line options");

        return options;
    }

    private static Option createAddTask() {
        return Option.builder("add")
                .required()
                .desc("Adds a new task")
                .argName("Add task")
                .hasArgs()
                .build();
    }

    private static Option createDeadline() {
        return Option.builder("d")
                .desc("Add deadline for the task")
                .argName("Deadline")
                .hasArg()
                .build();
    }
}
