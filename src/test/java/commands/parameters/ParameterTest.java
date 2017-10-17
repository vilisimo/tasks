package commands.parameters;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParameterTest {
    
    @Test
    public void errorMessageIsReachable() {
        DummyParameter parameter = new DummyParameter("test");
        String message = parameter.getErrorMessage();

        assertThat(message, is("test"));
    }


    private static class DummyParameter extends Parameter {

        private DummyParameter(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public State determineState() {
            return null;
        }
    }
}