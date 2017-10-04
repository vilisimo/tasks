import commands.Command;
import org.apache.commons.cli.Options;

import java.util.Optional;

public class Application {

    public static void main(String args[]) {
        Options options = CliOptions.createOptions();
        Optional<Command> command = ArgumentParser.parse(options, args);
    }
}
