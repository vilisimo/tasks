import org.apache.commons.cli.Options;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CliOptionsTest {

    @Test
    public void createsAddTask() {
        Options options = CliOptions.createOptions();

        assertNotNull(options.getOption("add"));
    }
}