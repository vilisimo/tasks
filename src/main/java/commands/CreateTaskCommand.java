package commands;

import configuration.JdbcConfiguration;
import datasource.Database;
import entities.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CreateTaskCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    private Task task;

    private CreateTaskCommand(String[] descriptionArray, Timestamp deadline) {
        String description = String.join(" ", descriptionArray);
        this.task = new Task(description, deadline);
    }

    @Override
    public void execute(JdbcConfiguration configuration) throws SQLException {
        logger.trace("Executing {} on {}", CreateTaskCommand.class.getSimpleName(), task.toString());
        Database db = new Database(configuration);

        String update = "INSERT INTO TASKS(description, deadline) VALUES (?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, task.getDescription());
            statement.setTimestamp(2, task.getDeadline());
            statement.executeUpdate();
            logger.trace("Successfully executed an update");

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                logger.info("Committed task to the database, id={}", resultSet.getLong(1));
            }
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
