package commands;

import datasource.Database;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClearTasksCommandTest {

    @Mock
    private Database database;

    private ClearTasksCommand command;

    @Test
    public void returnsEmptyWithEmptyTrue() {
        command = new ClearTasksCommand(true);

        assertThat(command.getState(), is(Command.State.VALID));
    }

    @Test
    public void returnsValidWithEmptyFalse() {
        command = new ClearTasksCommand(false);

        assertThat(command.getState(), is(Command.State.EMPTY));
    }

    @Test
    public void callsClearOnDatabase() throws SQLException {
        command = new ClearTasksCommand(true);

        command.executeCommand(database);

        verify(database).clear();
    }

    @Test(expected = RuntimeException.class)
    public void convertsSqlExceptionToRuntime() throws SQLException {
        command = new ClearTasksCommand(true);

        doThrow(new SQLException()).when(database).clear();

        command.executeCommand(database);
    }
}