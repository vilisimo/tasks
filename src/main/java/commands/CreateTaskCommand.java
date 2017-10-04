package commands;

import entities.Task;

public class CreateTaskCommand implements Command {

    private Task task;

    public CreateTaskCommand(String[] descriptionArray) {
        String description = String.join(" ", descriptionArray);
        this.task = new Task(description);
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
