import coloring.Printer;
import commands.Command;
import commands.CreateTaskCommand;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ArgumentParser {

    private static final Logger logger = LogManager.getLogger();

    public static Optional<Command> parse(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            logger.info("Parsed command line arguments");

            Optional<String[]> task = Optional.ofNullable(cmd.getOptionValues("add"));

            if (task.isPresent()) {
                logger.trace("Creating {}", CreateTaskCommand.class.getSimpleName());
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
