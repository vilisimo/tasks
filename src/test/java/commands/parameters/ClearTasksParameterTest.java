package commands.parameters;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ClearTasksParameterTest {

    private ClearTasksParameter parameter;

    @Test
    public void returnsEmptyWithEmptyTrue() {
        parameter = new ClearTasksParameter(true);

        assertThat(parameter.determineState(), is(Parameter.State.EMPTY));
    }

    @Test
    public void returnsValidWithEmptyFalse() {
        parameter = new ClearTasksParameter(false);

        assertThat(parameter.determineState(), is(Parameter.State.VALID));
    }

}