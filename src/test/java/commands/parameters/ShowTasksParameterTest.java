package commands.parameters;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ShowTasksParameterTest {

    @Test
    public void emptyParameterReturnsEmptyState() {
        ShowTasksParameter parameter = new ShowTasksParameter(true);

        assertThat(parameter.determineState(), is(Parameter.State.VALID));
    }

    @Test
    public void nonEmptyParameterReturnsValidState() {
        ShowTasksParameter parameter = new ShowTasksParameter(false);

        assertThat(parameter.determineState(), is(Parameter.State.EMPTY));
    }
}