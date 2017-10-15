package commands.parameters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Objects;

public class AddTaskParameter extends Parameter {

    private static final Logger logger = LogManager.getLogger();

    private final String description;
    private final Timestamp deadline;

    AddTaskParameter(String description, Timestamp deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    /**
     * If description is present, we can save the task in the database.
     *
     * If description is missing, it doesn't mean the parameters are
     * invalid. It could be that the user did not want to add a task.
     *
     * However, if description is null and deadline is not, it is clear
     * user wanted to do add a task with a deadline, but the parameters
     * supplied are insufficient to do so.
     *
     * @return state indicating if parameters are valid and can be acted
     *         upon.
     */
    @Override
    public Parameter.State determineState() {
        if (description != null) {
            return State.VALID;
        } else if (description == null && deadline != null) {
            this.errorMessage = "Description must be provided when adding a task";
            return State.INVALID;
        } else {
            return State.EMPTY;
        }
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddTaskParameter parameter = (AddTaskParameter) o;

        return Objects.equals(description, parameter.description) && Objects.equals(deadline, parameter.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, deadline);
    }


    public static class Builder {

        private String description;
        private Timestamp deadline;

        public Builder description(String[] description) {
            if (description != null) {
                this.description = String.join(" ", description);
                logger.trace("Adding description: {}", this.description);
            } else {
                this.description = null;
                logger.trace("Adding null description");
            }

            return this;
        }

        public Builder deadline(Timestamp deadline) {
            logger.trace("Adding deadline: {}", deadline);
            this.deadline = deadline;

            return this;
        }

        public AddTaskParameter build() {
            logger.trace("Building {}", AddTaskParameter.class.getSimpleName());

            return new AddTaskParameter(description, deadline);
        }
    }
}
