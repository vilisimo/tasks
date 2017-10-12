import cli.CliOptions;
import commands.Command;
import configuration.ConfigurationLoader;
import configuration.JdbcConfiguration;
import datasource.Database;
import org.apache.commons.cli.Options;

import java.util.Optional;

public class Application {

    public static void main(String args[]) {
        JdbcConfiguration jdbcConfig = ConfigurationLoader.loadJdbcConfig("db/server.properties");
        Options options = CliOptions.createOptions();
        ArgumentParser parser = new ArgumentParser();
        Optional<Command> command = parser.parse(options, args);
        command.ifPresent(cmd -> cmd.execute(new Database(jdbcConfig)));
    }
}
