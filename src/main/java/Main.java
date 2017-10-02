// To run through maven:
// >>> mvn exec:java -Dexec.args="--help"

import org.apache.commons.cli.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class Main {
    public static void main(String args[]) throws ParseException {
        createOptions(args);
        connect();
    }

    private static void createOptions(String args[]) throws ParseException {
        Options options = new Options();
        options.addOption("t", false, "display current time");

        // Option taskOption = new Option("task", "add todo tasks");
        // taskOption.setArgs(Option.UNLIMITED_VALUES);

        Option taskOption = Option.builder("task")
                .desc("add todo tasks")
                .hasArgs()
                .build();

        options.addOption(taskOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("t")) {
            System.out.println("Date is: " + new Date());
        } else {
            System.out.println("No data option supplied");
        }

        Optional<String[]> task = Optional.ofNullable(cmd.getOptionValues("task"));
        task.ifPresent(t -> System.out.println(Arrays.toString(t)));
    }

    // Before running the program, db needs to be started:
    // >>> java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/tododb --dbname.0 tododb
    private static void connect() {
        Connection connection;

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/tododb", "SA", "");

            if (connection != null) {
                System.out.println("Successfully established connection!");
            } else {
                System.out.println("Connection was not established...");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
