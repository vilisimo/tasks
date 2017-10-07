import coloring.Printer;
import commands.Command;
import commands.CreateTaskCommand;
import org.apache.commons.cli.*;

import java.util.Optional;

public class ArgumentParser {

    public static Optional<Command> parse(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            Optional<String[]> task = Optional.ofNullable(cmd.getOptionValues("add"));

            if (task.isPresent()) {
                return Optional.of(new CreateTaskCommand(task.get()));
            }

        } catch (MissingArgumentException e) {
            Printer.error("Please provide a task description!");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
