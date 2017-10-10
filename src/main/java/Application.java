import cli.CliOptions;
import commands.Command;
import configuration.ConfigurationLoader;
import configuration.JdbcConfiguration;
import datasource.Database;
import org.apache.commons.cli.Options;

import java.util.Optional;

/**
 * To run app with Maven:
 * >>> mvn clean install
 * >>> mvn exec:java -Dexec.args="arguments"
 */

public class Application {

    public static void main(String args[]) {
        JdbcConfiguration jdbcConfig = ConfigurationLoader.loadJdbcConfig("db/server.properties");
        Database database = new Database(jdbcConfig);

        Options options = CliOptions.createOptions();
        ArgumentParser parser = new ArgumentParser();
        Optional<Command> command = parser.parse(options, args);
        command.ifPresent(Command::execute);

        database.closeConnection();
    }
}
