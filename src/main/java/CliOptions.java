import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CliOptions {

    public static Options createOptions() {
        Options options = new Options();
        options.addOption(createAddTask());

        return options;
    }

    private static Option createAddTask() {
        return Option.builder("add")
                .desc("Adds a new todo task")
                .argName("Add task")
                .hasArgs()
                .build();
    }
}
