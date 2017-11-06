package commands;

import cli.CliOptions;
import cli.OptionNames;
import datasource.Database;
import org.apache.commons.cli.Options;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static cli.OptionNames.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PrintHelpCommandTest {

    @Mock
    private Database database;

    @Test
    public void hasValidStateWithExecutableTrue() {
        PrintHelpCommand command = PrintHelpCommand.from(true, new Options());
        assertThat(command.state, is(Command.State.VALID));
    }

    @Test
    public void setsEmptyState() {
        PrintHelpCommand command = PrintHelpCommand.from(false, null);

        assertThat(command.state, is(Command.State.EMPTY));
    }

    @Test
    public void executesWithValidState() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream standardOut = System.out;
        System.setOut(new PrintStream(out));

        PrintHelpCommand command = PrintHelpCommand.from(true, CliOptions.createOptions());
        command.executeCommand(database);

        assertThat(out.toString(), containsString(ADD.longOpt()));
        assertThat(out.toString(), containsString(DEADLINE.longOpt()));
        assertThat(out.toString(), containsString(CATEGORY.longOpt()));
        assertThat(out.toString(), containsString(FILTER.longOpt()));
        assertThat(out.toString(), containsString(REMOVE.longOpt()));
        assertThat(out.toString(), containsString(CLEAR.longOpt()));
        assertThat(out.toString(), containsString(HELP.longOpt()));

        System.setOut(standardOut);
    }
}