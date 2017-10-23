package utils;

import dates.DateParser;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

public final class Chronos {

    private Chronos() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static String instantLocalDate(Instant instant) {
        return instant == null ? "N/A" : LocalDate.from(instant.atZone(ZoneId.systemDefault())).toString();
    }

    public static Optional<Timestamp> convertDaysToTimestamp(String days) {
        if (days != null) {
            return Optional.of(DateParser.parseDate(days));
        }

        return Optional.empty();
    }
}
