package commands;

import datasource.Database;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FilterCategoryCommandTest {

    @Mock
    private Database database;

    private FilterTasksCommand command;

    @Before
    public void setup() {
        command = FilterTasksCommand.from("category");
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
    public void callsCorrectMethodWithStringAsArgument() throws SQLException {
        command.execute(database);

        verify(database).filter(eq("category"));
    }
}
