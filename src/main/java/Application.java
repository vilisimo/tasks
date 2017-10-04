import org.apache.commons.cli.Options;

public class Application {

    public static void main(String args[]) {
        Options options = CliOptions.createOptions();
        ArgumentParser.parse(options, args);
    }
}
