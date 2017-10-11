import coloring.Printer;
import commands.Command;
import commands.CreateTaskCommand;
import dates.DateParser;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Optional;

import static cli.OptionNames.ADD;
import static cli.OptionNames.DEADLINE;

public class ArgumentParser {

    private static final Logger logger = LogManager.getLogger();
    private CommandLineParser parser;

    public ArgumentParser() {
        this.parser = new DefaultParser();
    }

    public Optional<Command> parse(Options options, String[] args) {
        Optional<Command> command = Optional.empty();

        if (isEmpty(args)) {
            return command;
        }

        CreateTaskCommand.Builder commandBuilder = new CreateTaskCommand.Builder();

        try {
            CommandLine cmd = parser.parse(options, args);
            logger.info("Parsed command line arguments");

            Optional<String[]> task = Optional.ofNullable(cmd.getOptionValues(ADD));
            Optional<String> endDate = Optional.ofNullable(cmd.getOptionValue(DEADLINE));

            if (task.isPresent()) {
                logger.trace("Found description option");
                commandBuilder.description(task.get());
            }

            if (endDate.isPresent()) {
                logger.trace("Found deadline option");
                Timestamp date = DateParser.parseDate(endDate.get());
                commandBuilder.deadline(date);
            }

            command = Optional.of(commandBuilder.build());

        } catch (MissingArgumentException | MissingOptionException | NumberFormatException e) {
            Printer.error(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException("Command parsing has failed", e);
        }

        return command;
    }

    private static boolean isEmpty(String[] args) {
        return args.length < 1;
    }
}
