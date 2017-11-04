package coloring;

import entities.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import printing.Printer;
import utils.Chronos;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

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

    @Test
    public void printsTasks() {
        Task task = new Task(1, "description", "category",
                Instant.now(), Instant.now());

        Printer.printTasks(Collections.singletonList(task));

        assertThat(out.toString(), containsString("description"));
        assertThat(out.toString(), containsString("1"));
        assertThat(out.toString(), containsString(Chronos.instantToLocalDate(Instant.now())));
    }
}