package dates;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static dates.DateParser.parseDate;
import static dates.DateParser.parseDays;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DateParserTest {

    private LocalTime lastInstant = LocalTime.MAX;
    private LocalDateTime beforeMidnight = LocalDateTime.of(LocalDate.now(), lastInstant);

    @Test
    public void parsesString() {
        assertThat(parseDays("5"), is(5));
    }

    @Test
    public void parsesDate() {
        Timestamp date = parseDate("5");
        LocalDateTime actualDate = date.toLocalDateTime();

        assertThat(actualDate, is(beforeMidnight.plusDays(5)));
    }

    @Test
    public void parsesTodayDate() {
        Timestamp date = parseDate("0");
        LocalDateTime actualDate = date.toLocalDateTime();

        assertThat(actualDate, is(beforeMidnight));
    }

    @Test(expected = NumberFormatException.class)
    public void throwsExceptionUponInvalidArgument() {
        parseDays("invalid");
    }

    @Test
    public void convertsTodayToZero() {
        int actual = parseDays("today");

        assertThat(actual, is(0));
    }

    @Test
    public void convertsTomorrowToOne() {
        int actual = parseDays("tomorrow");

        assertThat(actual, is(1));
    }

    @Test
    public void ignoresCase() {
        int today = parseDays("TODAY");
        int tomorrow = parseDays("TOMORROW");

        assertThat(today, is(notNullValue()));
        assertThat(tomorrow, is(notNullValue()));
    }
}