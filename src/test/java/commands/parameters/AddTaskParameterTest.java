package commands.parameters;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AddTaskParameterTest {

    @Test
    public void setsValidState() {
        AddTaskParameter parameter = new AddTaskParameter("notNull", null);
        Parameter.State state = parameter.determineState();

        assertThat(state, is(Parameter.State.VALID));
    }

    @Test
    public void setsInvalidState() {
        AddTaskParameter parameter = new AddTaskParameter(null, Timestamp.from(Instant.now()));
        Parameter.State state = parameter.determineState();

        assertThat(state, is(Parameter.State.INVALID));
    }

    @Test
    public void setsEmptyState() {
        AddTaskParameter parameter = new AddTaskParameter(null, null);
        Parameter.State state = parameter.determineState();

        assertThat(state, is(Parameter.State.EMPTY));
    }

    @Test
    public void builderProvidesAllFields() {
        Timestamp deadline = Timestamp.from(Instant.now());
        String[] description = {"Test", "description"};
        AddTaskParameter parameter = new AddTaskParameter.Builder()
                .description(description)
                .deadline(deadline)
                .build();

        assertThat(parameter, is(new AddTaskParameter(String.join(" ", description), deadline)));
    }

    @Test
    public void builderHandlesNullDescription() {
        AddTaskParameter parameter = new AddTaskParameter.Builder()
                .description(null)
                .build();

        assertThat(parameter, is(new AddTaskParameter(null, null)));
    }
}