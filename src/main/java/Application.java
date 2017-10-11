import cli.CliOptions;
import coloring.Printer;
import commands.Command;
import configuration.ConfigurationLoader;
import configuration.JdbcConfiguration;
import org.apache.commons.cli.Options;

import java.sql.SQLException;
import java.util.Optional;

/**
 * To run app with Maven:
 * >>> mvn clean install
 * >>> mvn exec:java -Dexec.args="arguments"
 */

public class Application {

    public static void main(String args[]) {
        JdbcConfiguration jdbcConfig = ConfigurationLoader.loadJdbcConfig("db/server.properties");
        Options options = CliOptions.createOptions();
        ArgumentParser parser = new ArgumentParser();
        Optional<Command> command = parser.parse(options, args);

        command.ifPresent(cmd -> {
            try {
                cmd.execute(jdbcConfig);
            } catch (SQLException e) {
                Printer.error("SQL Error while executing the command");
            }
        });
    }
}
