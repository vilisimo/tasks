import cli.CliOptions;
import commands.AddTaskCommand;
import commands.Command;
import commands.RemoveTaskCommand;
import commands.ShowTasksCommand;
import org.apache.commons.cli.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
    public void returnsEmptyLitWithNoArgs() {
        List<Command> commands = parser.parse(new Options(), new String[] {});

        assertTrue(commands.isEmpty());
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

        parser.parse(options, new String[] {"-add"});

        assertThat(out.toString(), containsString("add"));

        System.setOut(standardOut);
    }

    @Test
    public void addsCreateShowCommand() {
        List<Command> commands = parser.parse(options, new String[] {"-show"});

        assertThat(commands, hasItem(isA(ShowTasksCommand.class)));
    }

    @Test
    public void addsEmptyCreateShowCommandEvenWhenNoArgument() {
        List<Command> commands = parser.parse(options, new String[] {"-add", "irrelevant"});

        assertThat(commands, hasItem(isA(ShowTasksCommand.class)));
    }

    @Test
    public void addsRemoveTaskCommand() {
        List<Command> commands = parser.parse(options, new String[] {"-del", "1"});

        assertThat(commands, hasItem(isA(RemoveTaskCommand.class)));
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

    @Test(expected = RuntimeException.class)
    public void throwsRuntimeWhenParsingFails() {
        parser.parse(options, new String[] {"-unrecognized command"});
    }
}