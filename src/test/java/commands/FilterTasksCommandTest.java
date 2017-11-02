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
public class FilterTasksCommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Database database;

    private FilterTasksCommand command;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final PrintStream standardOut = System.out;


    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(out));
        command = FilterTasksCommand.from("1");
    }

    @After
    public void resetStream() {
        System.setOut(standardOut);
    }

    @Test
    public void setsValidState() {
        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void setsEmptyState() {
        FilterTasksCommand command = FilterTasksCommand.from(null);

        assertThat(command.getState(), is(Command.State.EMPTY));
    }

    @Test
    public void callsCorrectMethodWhenDeadlineGivenAsStringInteger() throws SQLException {
        command.execute(database);

        verify(database).filter(any(Timestamp.class));
    }

    @Test
    public void callsCorrectMethodWhenDeadlineGivenAsToday() throws SQLException {
        command = FilterTasksCommand.from("today");

        command.executeCommand(database);

        verify(database).filter(any(Timestamp.class));
    }

    @Test
    public void callsCorrectMethodWhenDeadlineGivenAsTomorrow() throws SQLException {
        command = FilterTasksCommand.from("tomorrow");

        command.executeCommand(database);

        verify(database).filter(any(Timestamp.class));
    }

    @Test
    public void callsCorrectMethodWhenNoneAsFilterIsSpecified() throws SQLException {
        command = FilterTasksCommand.from("none");

        command.executeCommand(database);

        verify(database).filter(null);
    }
}