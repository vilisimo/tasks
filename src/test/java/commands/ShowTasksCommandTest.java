package commands;

import datasource.Database;
import entities.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowTasksCommandTest {

    @Mock
    private Database database;

    private ShowTasksCommand command;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final PrintStream standardOut = System.out;


    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(out));
        command = new ShowTasksCommand(true);
    }

    @After
    public void resetStream() {
        System.setOut(standardOut);
    }

    @Test
    public void setsValidState() {
        ShowTasksCommand command = new ShowTasksCommand(true);

        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void setsEmptyState() {
        ShowTasksCommand command = new ShowTasksCommand(false);

        assertThat(command.getState(), is(Command.State.EMPTY));
    }

    @Test
    public void handlesEmptyList() throws SQLException {
        when(database.getAll()).thenReturn(emptyList());

        command.executeCommand(database);

        assertThat(out.toString(), is(""));
    }

    @Test
    public void printsNonEmptyList() throws SQLException {
        List<Task> tasks = singletonList(new Task(1, "Test", Instant.now(), Instant.now()));
        when(database.getAll()).thenReturn(tasks);

        command.executeCommand(database);
        String output = out.toString();

        assertThat(output, containsString("1"));
        assertThat(output, containsString("Test"));
    }

    @Test
    public void printsEveryItem() throws SQLException {
        List<Task> tasks = List.of(
                new Task(1, "Test1", Instant.now(), Instant.now()),
                new Task(2, "Test2", Instant.now(), Instant.now()));
        when(database.getAll()).thenReturn(tasks);

        command.executeCommand(database);
        String output = out.toString();

        assertThat(output, containsString("Test1"));
        assertThat(output, containsString("Test2"));
    }

    @Test(expected = RuntimeException.class)
    public void convertsSqlExceptionToRuntime() throws SQLException {
        doThrow(SQLException.class).when(database).getAll();

        command.executeCommand(database);
    }
}