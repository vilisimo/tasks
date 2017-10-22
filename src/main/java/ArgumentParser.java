import coloring.Printer;
import commands.*;
import commands.parameters.AddTaskParameter;
import commands.parameters.ClearTasksParameter;
import commands.parameters.RemoveTaskParameter;
import commands.parameters.ShowTasksParameter;
import dates.DateParser;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
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

        AddTaskParameter.Builder addTaskBuilder = new AddTaskParameter.Builder();
        addTaskBuilder.description(cmd.getOptionValues(ADD));

        if (cmd.getOptionValue(DEADLINE) != null) {
            Timestamp date = DateParser.parseDate(cmd.getOptionValue(DEADLINE));
            addTaskBuilder.deadline(date);
        }

        return new AddTaskCommand(addTaskBuilder.build());
    }

    private ShowTasksCommand createShowTasksCommand(CommandLine cmd) {
        logger.trace("Creating {}", ShowTasksCommand.class.getSimpleName());

        if (cmd.hasOption(SHOW)) {
            return new ShowTasksCommand(new ShowTasksParameter(false));
        } else {
            return new ShowTasksCommand(new ShowTasksParameter(true));
        }
    }

    private RemoveTaskCommand createRemoveTaskCommand(CommandLine cmd) {
        logger.trace("Creating {}", RemoveTaskCommand.class.getSimpleName());

        if (cmd.getOptionValue(REMOVE) != null) {
            String removeValue = cmd.getOptionValue(REMOVE);
            try {
                RemoveTaskParameter parameter = new RemoveTaskParameter(Integer.parseInt(removeValue));
                return new RemoveTaskCommand(parameter);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("\"" + removeValue + "\" is not a number");
            }
        } else {
            RemoveTaskParameter parameter = new RemoveTaskParameter(null);

            return new RemoveTaskCommand(parameter);
        }
    }

    private ClearTasksCommand createClearTasksCommand(CommandLine cmd) {
        logger.trace("Creating {}", ClearTasksCommand.class.getSimpleName());

        if (cmd.hasOption(CLEAR)) {
            return new ClearTasksCommand(new ClearTasksParameter(false));
        } else {
            return new ClearTasksCommand(new ClearTasksParameter(true));
        }
    }
}
