package commands;

import commands.parameters.RemoveTaskParameter;
import datasource.Database;

public class RemoveTaskCommand extends Command<RemoveTaskParameter> {

    public RemoveTaskCommand(RemoveTaskParameter parameter) {
        super(parameter);
    }

    @Override
    void executeParameters(Database database) {
        System.out.println("Temporary placeholder");
    }
}
