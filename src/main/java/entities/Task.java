package entities;

import java.time.Instant;

public final class Task {

    private final int id;
    private final String description;
    private final String category;
    private final Instant created;
    private final Instant deadline;

    public Task(int id, String description, String category, Instant created, Instant deadline) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.created = created;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", created=" + created +
                ", deadline=" + deadline +
                '}';
    }
}
