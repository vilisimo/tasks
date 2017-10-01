// To run through maven:
// >>> mvn exec:java -Dexec.args="--help"

import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class Main {
    public static void main(String args[]) throws ParseException {
        Options options = new Options();
        options.addOption("t", false, "display current time");

        // Option taskOption = new Option("task", "add todo tasks");
        // taskOption.setArgs(Option.UNLIMITED_VALUES);

        Option taskOption = Option.builder("task")
                .desc("add todo tasks")
                .hasArgs()
                .build();

        options.addOption(taskOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("t")) {
            System.out.println(new Date());
        } else {
            System.out.println("No data option supplied");
        }

        Optional<String[]> task = Optional.ofNullable(cmd.getOptionValues("task"));
        task.ifPresent(t -> System.out.println(Arrays.toString(t)));
    }
}
