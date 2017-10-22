package commands;

import datasource.Database;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddTaskCommandTest {

    @Mock
    private Database database;

    private AddTaskCommand command;

    @Test
    public void handlesNullDescription() {
        command = AddTaskCommand.from(null, "1");

        assertThat(command.getDescription(), is(nullValue()));
    }

    @Test
    public void handlesNonNullDescription() {
        command = AddTaskCommand.from(new String[] {"test", "description"}, "1");

        assertThat(command.getDescription(), is("test description"));
    }

    @Test
    public void handlesNullDaysToDeadline() {
        command = AddTaskCommand.from(new String[] {"test"}, null);

        assertThat(command.getDeadline(), is(nullValue()));
    }

    @Test
    public void handlesNonNullDaysToDeadline() {
        command = AddTaskCommand.from(new String[] {"test"}, "1");

        assertThat(command.getDeadline(), is(notNullValue()));
    }

    @Test
    public void setsValidState() {
        command = AddTaskCommand.from(new String[] {"notNull"}, null);

        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void setsValidStateWithNonNullDaysToDeadline() {
        command = AddTaskCommand.from(new String[] {"notNull"}, "1");

        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void setsInvalidState() {
        command = AddTaskCommand.from(null, "0");

        assertThat(command.getState(), is(Command.State.INVALID));
    }

    @Test
    public void setsEmptyState() {
        command = AddTaskCommand.from(null, null);

        assertThat(command.getState(), is(Command.State.EMPTY));
    }

    @Test
    public void saveIsCalledWithValidState() throws SQLException {
        command = AddTaskCommand.from(new String[] {"description"}, "1");

        command.execute(database);

        verify(database).save(eq(command));
    }

    @Test
    public void saveIsNotCalledWithInvalidState() throws SQLException {
        command = AddTaskCommand.from(null, "1");

        command.execute(database);

        verifyZeroInteractions(database);
    }

    @Test
    public void saveIsNotCalledWithEmptyState() throws SQLException {
        command = AddTaskCommand.from(null, null);

        command.execute(database);

        assertThat(command.getState(), is(Command.State.EMPTY));
        verifyZeroInteractions(database);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionConvertedToRuntime() throws SQLException {
        command = AddTaskCommand.from(new String[] {"description"}, "1");
        doThrow(new SQLException()).when(database).save(eq(command));

        command.executeCommand(database);
    }
}