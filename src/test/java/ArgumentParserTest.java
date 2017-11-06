import cli.CliOptions;
import commands.*;
import org.apache.commons.cli.Options;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * This is more of an integration test suite, as some of
 * the classes are quite hard to mock or even reach.
 * Hence, real classes from CommonsCli are used.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ArgumentParserTest {

    private ArgumentParser parser = new ArgumentParser();
    private Options options = CliOptions.createOptions();

    @Test
    public void returnsShowAllTasksCommandWithNoArguments() {
        List<Command> commands = parser.parse(new Options(), new String[] {});

        assertThat(commands.size(), is(1));
        assertThat(commands, hasItem(isA(ShowTasksCommand.class)));
    }

    @Test
    public void addsCreateTaskCommand() {
        List<Command> commands = parser.parse(options, new String[] {"-add", "irrelevant"});

        assertThat(commands, hasItem(isA(AddTaskCommand.class)));
    }

    @Test
    public void informsAboutInvalidNumber() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        parser.parse(options, new String[] {"-add", "description", "-d", "invalid number"});

        assertThat(out.toString(), containsString("is not a number"));

        System.setOut(standardOut);
    }

    @Test
    public void informsAboutMissingArguments() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        parser.parse(options, new String[] {"-a"});

        assertThat(out.toString(), containsString("a"));

        System.setOut(standardOut);
    }

    @Test
    public void addsCreateShowCommand() {
        List<Command> commands = parser.parse(options, new String[] {"-filter", "1"});

        assertThat(commands, hasItem(isA(FilterTasksCommand.class)));
    }

    @Test
    public void addsEmptyCreateShowCommandEvenWhenNoArgument() {
        List<Command> commands = parser.parse(options, new String[] {"-add", "irrelevant"});

        assertThat(commands, hasItem(isA(FilterTasksCommand.class)));
    }

    @Test
    public void addsRemoveTaskCommand() {
        List<Command> commands = parser.parse(options, new String[] {"-rm", "1"});

        assertThat(commands, hasItem(isA(RemoveTaskCommand.class)));
    }

    @Test
    public void addsClearTasksCommand() {
        List<Command> commands = parser.parse(options, new String[] {"-clear"});

        assertThat(commands, hasItem(isA(ClearTasksCommand.class)));
    }

    @Test
    public void addsPrintHelpCommand() {
        List<Command> commands = parser.parse(options, new String[] {"-help"});

        assertThat(commands, hasItem(isA(PrintHelpCommand.class)));
    }

    @Test
    public void informsAboutInvalidTaskId() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        parser.parse(options, new String[] {"-del", "invalid number"});

        assertThat(out.toString(), containsString("is not a number"));

        System.setOut(standardOut);
    }

    @Test
    public void informsAboutParsingFailures() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        parser.parse(options, new String[] {"-unrecognized command"});

        assertThat(out.toString(), containsString("Unrecognized option"));

        System.setOut(standardOut);
    }
}