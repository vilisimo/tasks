import commands.Command;
import configuration.ConfigurationLoader;
import configuration.JdbcConfiguration;
import org.apache.commons.cli.Options;

import java.util.Optional;

public class Application {

    public static void main(String args[]) {
        JdbcConfiguration dbConfiguration = ConfigurationLoader.loadJdbcConfig("server.properties");
        Options options = CliOptions.createOptions();
        Optional<Command> command = ArgumentParser.parse(options, args);
    }
}
