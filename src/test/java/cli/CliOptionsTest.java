package cli;

import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CliOptionsTest {

    private Options options;

    @Before
    public void setup() {
        options = CliOptions.createOptions();
    }

    @Test
    public void createsAddTaskOption() {
        assertNotNull(options.getOption("add"));
    }

    @Test
    public void createsDeadlineOption() {
        assertNotNull(options.getOption("d"));
    }
}