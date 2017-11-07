package commands;

import dates.DateParser;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Base class for task filtering commands.
 */
public abstract class FilterTasksCommand extends Command {

    boolean executable;

    public static FilterTasksCommand from(String optionValue) {
        try {
            Timestamp filter = Optional.ofNullable(optionValue).map(FilterTasksCommand::parseDeadline).orElse(null);
            return new FilterDeadlineCommand(optionValue != null, filter);
        } catch (NumberFormatException e) {

            return new FilterCategoryCommand(optionValue != null, optionValue);
        }
    }

    private static Timestamp parseDeadline(String daysToDeadline) {
        if (!daysToDeadline.equalsIgnoreCase("none")) {
            return DateParser.parseDate(daysToDeadline);
        }

        return null;
    }

    @Override
    protected final void determineState() {
        if (executable) {
            this.state = State.VALID;
        } else {
            this.state = State.EMPTY;
        }
    }
}
