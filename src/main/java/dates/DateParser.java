package dates;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static dates.Days.TODAY;
import static dates.Days.TOMORROW;

/**
 * Utility class that concerns itself with parsing dates
 * from Strings.
 */
public final class DateParser {

    private DateParser() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static Timestamp parseDate(String daysFromNow) {
        LocalDateTime beforeMidnight = beforeMidnight();

        int days = parseDays(daysFromNow);
        LocalDateTime futureDate = beforeMidnight.plusDays(days);

        return Timestamp.valueOf(futureDate);
    }

    private static LocalDateTime beforeMidnight() {
        LocalTime beforeMidnight = LocalTime.MAX;
        LocalDate today = LocalDate.now();

        return LocalDateTime.of(today, beforeMidnight);
    }

    static int parseDays(String days) {
        try {
            if (days.toLowerCase().equals("today")) {
                return TODAY.ordinal();
            } else if (days.toLowerCase().equals("tomorrow")) {
                return TOMORROW.ordinal();
            } else {
                return Integer.parseInt(days);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("\"" + days + "\" is not a number");
        }
    }
}
