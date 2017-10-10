package commands;

import coloring.Printer;
import entities.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;

public class CreateTaskCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    private Task task;

    private CreateTaskCommand(String[] descriptionArray, Instant deadline) {
        String description = String.join(" ", descriptionArray);
        this.task = new Task(description, deadline);
    }

    @Override
    public void execute() {
        logger.trace("Executing {} on {}", CreateTaskCommand.class.getSimpleName(), task.toString());
        Printer.warning("BE ADVISED: this method should be fully implemented before use");
    }

    public static class Builder {
        private String[] description;
        private Instant deadline = null;

        public Builder description(String[] description) {
            this.description = description;
            return this;
        }

        public Builder deadline(Instant deadline) {
            this.deadline = deadline;
            return this;
        }

        public CreateTaskCommand build() {
            logger.trace("Creating {}", CreateTaskCommand.class.getSimpleName());
            return new CreateTaskCommand(description, deadline);
        }
    }
}
