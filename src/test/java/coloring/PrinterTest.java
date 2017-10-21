package coloring;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class PrinterTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final PrintStream standardOut = System.out;


    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void resetStream() {
        System.setOut(standardOut);
    }

    @Test
    public void successSmellsGreen() {
        Printer.success("test");

        assertThat(out.toString(), containsString(Colors.ANSI_GREEN.toString()));
        assertThat(out.toString(), containsString("test"));
    }

    @Test
    public void successContainsResetSequence() {
        Printer.success("test");

        assertThat(out.toString(), containsString(Colors.ANSI_RESET.toString()));
    }

    @Test
    public void errorSmellsRed() {
        Printer.error("test");

        assertThat(out.toString(), containsString(Colors.ANSI_RED.toString()));
        assertThat(out.toString(), containsString("test"));
    }

    @Test
    public void errorContainsResetSequence() {
        Printer.error("test");

        assertThat(out.toString(), containsString(Colors.ANSI_RESET.toString()));
    }


    @Test
    public void warningSmellsYellow() {
        Printer.warning("test");

        assertThat(out.toString(), containsString(Colors.ANSI_YELLOW.toString()));
        assertThat(out.toString(), containsString("test"));
    }

    @Test
    public void warningContainsResetSequence() {
        Printer.warning("test");

        assertThat(out.toString(), containsString(Colors.ANSI_RESET.toString()));
    }
}