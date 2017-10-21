package commands;

import commands.parameters.RemoveTaskParameter;
import datasource.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RemoveTaskCommandTest {

    @Mock
    private Database database;

    @Mock
    private RemoveTaskParameter parameter;

    private RemoveTaskCommand command;

    @Before
    public void setup() {
        command = new RemoveTaskCommand(parameter);
    }

    @Test
    public void executesDelete() throws SQLException {
        command.executeParameters(database);

        verify(database).delete(any(RemoveTaskParameter.class));
    }

    @Test(expected = RuntimeException.class)
    public void convertsSqlExceptionToRuntimeException() throws SQLException {
        doThrow(new SQLException()).when(database).delete(any(RemoveTaskParameter.class));

        command.executeParameters(database);
    }
}