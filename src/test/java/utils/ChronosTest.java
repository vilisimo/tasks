package utils;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ChronosTest {

    @Test
    public void localDateAndInstantMatch() {
        LocalDate date = LocalDate.now();

        String instantToDate = Chronos.instantToLocalDate(Instant.now());

        assertThat(date.toString(), is(instantToDate));
    }

    @Test
    public void handlesNull() {
        String date = Chronos.instantToLocalDate(null);

        assertThat(date, is("N/A"));
    }

    @Test
    public void convertsDaysToTimestamp() {
        Optional<Timestamp> stamp = Chronos.convertDaysToTimestamp("1");

        assertThat(stamp.orElse(null), is(notNullValue()));
    }

    @Test
    public void returnsEmptyOptionalUponNullDay() {
        Optional<Timestamp> stamp = Chronos.convertDaysToTimestamp(null);

        assertThat(stamp.orElse(null), is(nullValue()));
    }
}