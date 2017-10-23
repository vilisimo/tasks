package utils;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChronosTest {

    @Test
    public void localDateAndInstantMatch() {
        LocalDate date = LocalDate.now();

        String instantToDate = Chronos.instantLocalDate(Instant.now());

        assertThat(date.toString(), is(instantToDate));
    }

    @Test
    public void handlesNull() {
        String date = Chronos.instantLocalDate(null);

        assertThat(date, is("N/A"));
    }
}