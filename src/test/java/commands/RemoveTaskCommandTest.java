package commands;

import datasource.Database;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Database database;

    private RemoveTaskCommand command;

    @Test
    public void stateIsValidWithPositiveId() {
        command = RemoveTaskCommand.from("1");

        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void stateIsInvalidWithNegativeId() {
        command = RemoveTaskCommand.from("-1");

        assertThat(command.getState(), is(Command.State.INVALID));
    }

    @Test
    public void stateIsEmptyWithNullId() {
        command = RemoveTaskCommand.from(null);

        assertThat(command.getState(), is(Command.State.EMPTY));
    }

    @Test
    public void executesDelete() throws SQLException {
        command = RemoveTaskCommand.from("1");

        command.executeCommand(database);

        verify(database).delete(eq(command));
    }

    @Test(expected = RuntimeException.class)
    public void convertsSqlExceptionToRuntimeException() throws SQLException {
        command = RemoveTaskCommand.from("2");

        doThrow(new SQLException()).when(database).delete(eq(command));

        command.executeCommand(database);
    }

    @Test
    public void reportsInvalidInput() {
        expectedException.expectMessage("\"invalid\" is not a number");
        expectedException.expect(NumberFormatException.class);

        RemoveTaskCommand.from("invalid");
    }
}