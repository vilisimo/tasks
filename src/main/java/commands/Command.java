package commands;

import datasource.Database;

public interface Command {
    void execute(Database database);
}
