package commands.parameters;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RemoveTaskParameterTest {

    private RemoveTaskParameter parameter;

    @Test
    public void stateIsValidWithPositiveId() {
        parameter = new RemoveTaskParameter(1);

        assertThat(parameter.determineState(), is(Parameter.State.VALID));
    }

    @Test
    public void whenStateIsValidErrorMessageIsNotSet() {
        parameter = new RemoveTaskParameter(1);

        assertThat(parameter.getErrorMessage(), is(nullValue()));
    }

    @Test
    public void stateIsInvalidWithNegativeId() {
        parameter = new RemoveTaskParameter(-1);

        assertThat(parameter.determineState(), is(Parameter.State.INVALID));
    }

    @Test
    public void whenStateIsInvalidErrorMessageIsSet() {
        parameter = new RemoveTaskParameter(-1);

        parameter.determineState();

        assertThat(parameter.getErrorMessage(), is(notNullValue()));
    }
}