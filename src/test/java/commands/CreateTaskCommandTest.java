package commands;

import datasource.Database;
import entities.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateTaskCommandTest {

    @Mock
    private Database database;

    private CreateTaskCommand command;

    @Before
    public void setup() {
        String[] description = {"test", "description"};

        command = new CreateTaskCommand.Builder()
                .deadline(Timestamp.from(Instant.now()))
                .description(description)
                .build();
    }

    @Test
    public void saveIsCalled() throws SQLException {
        command.execute(database);

        verify(database).save(any(Task.class));
    }

    @Test(expected = RuntimeException.class)
    public void exceptionConvertedToRuntime() throws SQLException {
        doThrow(new SQLException()).when(database).save(any());

        command.execute(database);
    }
}