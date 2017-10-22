package commands;

import commands.parameters.ClearTasksParameter;
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
public class ClearTasksCommandTest {

    @Mock
    private Database database;

    @Mock
    private ClearTasksParameter parameter;

    private ClearTasksCommand command;

    @Before
    public void setup() {
        command = new ClearTasksCommand(parameter);
    }

    @Test
    public void callsClearOnDatabase() throws SQLException {
        command.executeParameters(database);

        verify(database).clear(any(ClearTasksParameter.class));
    }

    @Test(expected = RuntimeException.class)
    public void convertsSqlExceptionToRuntime() throws SQLException {
        doThrow(new SQLException()).when(database).clear(any(ClearTasksParameter.class));

        command.executeParameters(database);
    }
}