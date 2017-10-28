import coloring.Printer;
import commands.*;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static cli.OptionNames.*;

class ArgumentParser {

    private static final Logger logger = LogManager.getLogger();

    private CommandLineParser parser;

    ArgumentParser() {
        this.parser = new DefaultParser();
    }

    List<Command> parse(Options options, String[] args) {
        List<Command> commands = new ArrayList<>();

        if (isEmpty(args)) {
            return commands;
        }

        try {
            CommandLine cmd = parser.parse(options, args);
            logger.info("Parsed command line arguments");

            commands.add(createAddTaskCommand(cmd));
            commands.add(createShowTasksCommand(cmd));
            commands.add(createRemoveTaskCommand(cmd));
            commands.add(createClearTasksCommand(cmd));

        } catch (MissingArgumentException | NumberFormatException e) {
            Printer.error(e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException("Command parsing has failed: " + e.getMessage().toLowerCase(), e);
        }

        return commands;
    }

    private static boolean isEmpty(String[] args) {
        return args.length < 1;
    }

    private AddTaskCommand createAddTaskCommand(CommandLine cmd) {
        logger.trace("Creating {}", AddTaskCommand.class.getSimpleName());

        return AddTaskCommand.from(cmd.getOptionValues(ADD), cmd.getOptionValue(DEADLINE));
    }

    private ShowTasksCommand createShowTasksCommand(CommandLine cmd) {
        logger.trace("Creating {}", ShowTasksCommand.class.getSimpleName());

        return ShowTasksCommand.from(cmd.hasOption(SHOW), cmd.getOptionValue(SHOW));
    }

    private RemoveTaskCommand createRemoveTaskCommand(CommandLine cmd) {
        logger.trace("Creating {}", RemoveTaskCommand.class.getSimpleName());

        if (cmd.getOptionValue(REMOVE) != null) {
            String removeValue = cmd.getOptionValue(REMOVE);
            try {
                return new RemoveTaskCommand(Integer.parseInt(removeValue));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("\"" + removeValue + "\" is not a number");
            }
        } else {
            return new RemoveTaskCommand(null);
        }
    }

    private ClearTasksCommand createClearTasksCommand(CommandLine cmd) {
        logger.trace("Creating {}", ClearTasksCommand.class.getSimpleName());

        return new ClearTasksCommand(cmd.hasOption(CLEAR));
    }
}
