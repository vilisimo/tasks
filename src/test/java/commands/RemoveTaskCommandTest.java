package commands;

import datasource.Database;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RemoveTaskCommandTest {

    @Mock
    private Database database;

    private RemoveTaskCommand command;

    @Test
    public void stateIsValidWithPositiveId() {
        command = new RemoveTaskCommand(1);

        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void stateIsInvalidWithNegativeId() {
        command = new RemoveTaskCommand(-1);

        assertThat(command.getState(), is(Command.State.INVALID));
    }

    @Test
    public void stateIsEmptyWithNullId() {
        command = new RemoveTaskCommand(null);

        assertThat(command.getState(), is(Command.State.EMPTY));
    }

    @Test
    public void executesDelete() throws SQLException {
        command = new RemoveTaskCommand(1);

        command.executeCommand(database);

        verify(database).delete(eq(command));
    }

    @Test(expected = RuntimeException.class)
    public void convertsSqlExceptionToRuntimeException() throws SQLException {
        command = new RemoveTaskCommand(2);

        doThrow(new SQLException()).when(database).delete(eq(command));

        command.executeCommand(database);
    }
}