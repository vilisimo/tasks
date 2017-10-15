package commands;

import commands.parameters.AddTaskParameter;
import commands.parameters.Parameter;
import datasource.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddTaskCommandTest {

    @Mock
    private Database database;

    @Mock
    private AddTaskParameter parameter;

    private AddTaskCommand command;

    @Before
    public void setup() {
        command = new AddTaskCommand(parameter);
    }

    @Test
    public void saveIsCalledWithValidState() throws SQLException {
        when(parameter.determineState()).thenReturn(Parameter.State.VALID);

        command.execute(database);

        verify(database).save(any(AddTaskParameter.class));
    }

    @Test
    public void saveIsNotCalledWithInvalidState() throws SQLException {
        when(parameter.determineState()).thenReturn(Parameter.State.INVALID);
        when(parameter.getErrorMessage()).thenReturn("Message from test method");

        command.execute(database);

        verifyZeroInteractions(database);
    }

    @Test
    public void saveIsNotCalledWithEmptyState() throws SQLException {
        when(parameter.determineState()).thenReturn(Parameter.State.EMPTY);

        command.execute(database);

        verifyZeroInteractions(database);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionConvertedToRuntime() throws SQLException {
        doThrow(new SQLException()).when(database).save(any(AddTaskParameter.class));

        command.executeParameters(database);
    }
}