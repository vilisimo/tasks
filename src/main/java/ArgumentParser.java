import coloring.Printer;
import commands.Command;
import commands.CreateTaskCommand;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.Optional;

public class ArgumentParser {

    private static final Logger logger = LogManager.getLogger();

    public static Optional<Command> parse(Options options, String[] args) {
        Optional<Command> command = Optional.empty();

        if (args.length < 1) {
            return command;
        }

        CreateTaskCommand.Builder commandBuilder = new CreateTaskCommand.Builder();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            logger.info("Parsed command line arguments");

            Optional<String[]> task = Optional.ofNullable(cmd.getOptionValues("add"));
            Optional<String> endDate = Optional.ofNullable(cmd.getOptionValue("d"));

            if (task.isPresent()) {
                logger.trace("Found description option");
                commandBuilder.description(task.get());
            }

            if (endDate.isPresent()) {
                logger.trace("Found deadline option");
                commandBuilder.deadline(Instant.now());
            }

            logger.trace("Creating {}", CreateTaskCommand.class.getSimpleName());
            command = Optional.of(commandBuilder.build());

        } catch (MissingArgumentException | MissingOptionException e) {
            Printer.error(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException("Command parsing has failed", e);
        }

        return command;
    }
}
