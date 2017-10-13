package entities;

import java.sql.Timestamp;

public final class Task {

    private final String description;
    private final Timestamp deadline;

    public Task(String description, Timestamp deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
