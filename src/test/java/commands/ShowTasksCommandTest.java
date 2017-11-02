package commands;

import datasource.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShowTasksCommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Database database;

    private ShowTasksCommand command;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final PrintStream standardOut = System.out;


    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(out));
        command = ShowTasksCommand.from(true, null);
    }

    @After
    public void resetStream() {
        System.setOut(standardOut);
    }

    @Test
    public void setsValidState() {
        ShowTasksCommand command = ShowTasksCommand.from(true, null);

        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void setsValidStateWithOptionalArgument() {
        ShowTasksCommand command = ShowTasksCommand.from(true, "1");

        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void setsEmptyState() {
        ShowTasksCommand command = ShowTasksCommand.from(false, null);

        assertThat(command.getState(), is(Command.State.EMPTY));
    }

    @Test
    public void callsCorrectMethodWhenDeadlineGiven() throws SQLException {
        command = ShowTasksCommand.from(true, "1");

        command.execute(database);

        verify(database).filter(any(Timestamp.class));
    }
}