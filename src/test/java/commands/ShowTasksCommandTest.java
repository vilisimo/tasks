package commands;

import datasource.Database;
import entities.Task;
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
import java.time.Instant;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
        command = new ShowTasksCommand(true);
        System.setOut(new PrintStream(out));
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
        command = new ShowTasksCommand(false);

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
        List<Task> tasks = singletonList(new Task(1, "Test", "Category",
                Instant.now(), Instant.now()));
        when(database.getAll()).thenReturn(tasks);

        command.executeCommand(database);
        String output = out.toString();

        assertThat(output, containsString("1"));
        assertThat(output, containsString("Test"));
        assertThat(output, containsString("Category"));
    }

    @Test
    public void printsEveryItem() throws SQLException {
        List<Task> tasks = List.of(
                new Task(1, "Test1", "Category", Instant.now(), Instant.now()),
                new Task(2, "Test2", "Category", Instant.now(), Instant.now()));
        when(database.getAll()).thenReturn(tasks);

        command.executeCommand(database);
        String output = out.toString();

        assertThat(output, containsString("Test1"));
        assertThat(output, containsString("Test2"));
    }


    @Test
    public void rejectsNullList() throws SQLException {
        when(database.getAll()).thenReturn(null);

        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Task list should not be null");

        command.executeCommand(database);
    }

    @Test(expected = RuntimeException.class)
    public void convertsSqlExceptionToRuntime() throws SQLException {
        doThrow(SQLException.class).when(database).getAll();

        command.executeCommand(database);
    }
}