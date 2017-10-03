import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.Optional;

// TODO: mutually exclusive groups. I.e., if add task is specified
// TODO: saving to database

public class ArgumentParser {

    public static void parse(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            Optional<String[]> task = Optional.ofNullable(cmd.getOptionValues("add"));
            task.ifPresentOrElse(t -> Arrays.stream(t).forEach(System.out::println), () -> System.out.println("Nothing added"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
