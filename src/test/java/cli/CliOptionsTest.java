package cli;

import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import static cli.OptionNames.*;
import static org.junit.Assert.assertNotNull;

public class CliOptionsTest {

    private Options options;

    @Before
    public void setup() {
        options = CliOptions.createOptions();
    }

    @Test
    public void createsAddTaskOption() {
        assertNotNull(options.getOption(ADD));
    }

    @Test
    public void createsDeadlineOption() {
        assertNotNull(options.getOption(DEADLINE));
    }

    @Test
    public void createsRemoveOption() {
        assertNotNull(options.getOption(REMOVE));
    }

    @Test
    public void createsClearOption() {
        assertNotNull(options.getOption(CLEAR));
    }
    
    @Test
    public void createsShowOption() {
        assertNotNull(options.getOption(FILTER));
    }
}