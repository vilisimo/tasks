package commands;

import datasource.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.sql.Timestamp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FilterDeadlineCommandTest {

    @Mock
    private Database database;

    private FilterTasksCommand command;


    @Before
    public void setup() {
        command = FilterTasksCommand.from("1");
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

        verify(database).filter((Timestamp) null);
    }
}