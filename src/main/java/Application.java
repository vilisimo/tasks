import cli.CliOptions;
import commands.Command;
import configuration.ConfigurationLoader;
import configuration.JdbcConfiguration;
import datasource.Database;
import datasource.DatabaseFactory;
import org.apache.commons.cli.Options;

import java.util.List;

public class Application {

    public static void main(String args[]) {
        JdbcConfiguration jdbcConfig = ConfigurationLoader.loadJdbcConfig("db/db.properties");
        Database database = DatabaseFactory.createDatabase(jdbcConfig);
        Options options = CliOptions.createOptions();
        ArgumentParser parser = new ArgumentParser();
        List<Command> commands = parser.parse(options, args);
        commands.forEach(cmd -> cmd.execute(database));
    }
}
