package commands;

import datasource.Database;
import entities.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Timestamp;

public class CreateTaskCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    private Task task;

    private CreateTaskCommand(String[] descriptionArray, Timestamp deadline) {
        String description = String.join(" ", descriptionArray);
        this.task = new Task(description, deadline);
    }

    @Override
    public void execute(Database database) {
        logger.trace("Executing {} on {}", CreateTaskCommand.class.getSimpleName(), task.toString());

        try {
            database.save(task);
        } catch (SQLException e) {
            throw new RuntimeException("SQL execution failed", e);
        }
    }

    public static class Builder {
        private String[] description;
        private Timestamp deadline = null;

        public Builder description(String[] description) {
            this.description = description;
            return this;
        }

        public Builder deadline(Timestamp deadline) {
            this.deadline = deadline;
            return this;
        }

        public CreateTaskCommand build() {
            logger.trace("Creating {}", CreateTaskCommand.class.getSimpleName());
            return new CreateTaskCommand(description, deadline);
        }
    }
}
