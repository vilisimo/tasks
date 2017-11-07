import commands.*;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import printing.Printer;

import java.util.ArrayList;
import java.util.List;

import static cli.OptionNames.*;

/**
 * Class that does the parsing of user's input.
 */
class ArgumentParser {

    private static final Logger logger = LogManager.getLogger();

    private CommandLineParser parser;

    ArgumentParser() {
        this.parser = new DefaultParser();
    }

    List<Command> parse(Options options, String[] args) {
        List<Command> commands = new ArrayList<>();

        if (isEmpty(args)) {
            commands.add(createShowAllTasksCommand());

            return commands;
        }

        try {
            CommandLine cmd = parser.parse(options, args);
            logger.info("Parsed command line arguments");

            commands.add(createPrintHelpCommand(cmd, options));
            commands.add(createAddTaskCommand(cmd));
            commands.add(createFilterTasksCommand(cmd));
            commands.add(createRemoveTaskCommand(cmd));
            commands.add(createClearTasksCommand(cmd));

        } catch (MissingArgumentException e) {
            Printer.error("Missing argument error [" + e.getMessage() + "]");
        } catch (NumberFormatException e) {
            Printer.error("Number parsing error [" + e.getMessage() + "]");
        } catch (ParseException e) {
            Printer.error("Command parsing error [" + e.getMessage() + "]");
        }

        return commands;
    }

    private static boolean isEmpty(String[] args) {
        return args.length < 1;
    }

    private PrintHelpCommand createPrintHelpCommand(CommandLine cmd, Options options) {
        logger.trace("Creating {}", PrintHelpCommand.class.getSimpleName());

        return PrintHelpCommand.from(cmd.hasOption(HELP.shortOpt()), options);
    }

    private AddTaskCommand createAddTaskCommand(CommandLine cmd) {
        logger.trace("Creating {}", AddTaskCommand.class.getSimpleName());

        return AddTaskCommand.from(cmd.getOptionValues(ADD.shortOpt()), cmd.getOptionValue(DEADLINE.shortOpt()),
                cmd.getOptionValues(CATEGORY.shortOpt()));
    }

    private ShowTasksCommand createShowAllTasksCommand() {
        logger.trace("Creating {}", ShowTasksCommand.class.getSimpleName());

        return new ShowTasksCommand(true);
    }

    private FilterTasksCommand createFilterTasksCommand(CommandLine cmd) {
        logger.trace("Creating {}", FilterTasksCommand.class.getSimpleName());

        return FilterTasksCommand.from(cmd.getOptionValue(FILTER.shortOpt()));
    }

    private RemoveTaskCommand createRemoveTaskCommand(CommandLine cmd) {
        logger.trace("Creating {}", RemoveTaskCommand.class.getSimpleName());

        return RemoveTaskCommand.from(cmd.getOptionValue(REMOVE.shortOpt()));
    }

    private ClearTasksCommand createClearTasksCommand(CommandLine cmd) {
        logger.trace("Creating {}", ClearTasksCommand.class.getSimpleName());

        return new ClearTasksCommand(cmd.hasOption(CLEAR.shortOpt()));
    }
}
