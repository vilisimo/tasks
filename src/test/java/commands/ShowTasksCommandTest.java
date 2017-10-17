package commands;

import commands.parameters.ShowTasksParameter;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowTasksCommandTest {

    @Mock
    private Database database;

    @Mock
    private ShowTasksParameter parameter;

    private ShowTasksCommand command;

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();


    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(out));
        command = new ShowTasksCommand(parameter);
    }

    @After
    public void resetStream() {
        System.setOut(null);
    }

    @Test
    public void handlesEmptyList() throws SQLException {
        when(database.getAll(any(ShowTasksParameter.class))).thenReturn(emptyList());

        command.executeParameters(database);

        assertThat(out.toString(), is(""));
    }

    @Test
    public void printsNonEmptyList() throws SQLException {
        List<Task> tasks = singletonList(new Task(1, "Test", Instant.now(), Instant.now()));
        when(database.getAll(any(ShowTasksParameter.class))).thenReturn(tasks);

        command.executeParameters(database);
        String output = out.toString();

        assertThat(output, containsString("1"));
        assertThat(output, containsString("Test"));
    }

    @Test
    public void printsEveryItem() throws SQLException {
        List<Task> tasks = List.of(
                new Task(1, "Test1", Instant.now(), Instant.now()),
                new Task(2, "Test2", Instant.now(), Instant.now()));
        when(database.getAll(any(ShowTasksParameter.class))).thenReturn(tasks);

        command.executeParameters(database);
        String output = out.toString();

        assertThat(output, containsString("Test1"));
        assertThat(output, containsString("Test2"));
    }
    
    @Test(expected = RuntimeException.class)
    public void convertsSqlExceptionToRuntime() throws SQLException {
        doThrow(SQLException.class).when(database).getAll(any(ShowTasksParameter.class));

        command.executeParameters(database);
    }
}