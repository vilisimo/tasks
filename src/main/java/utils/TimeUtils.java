package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public final class TimeUtils {

    private TimeUtils() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static String instantLocalDate(Instant instant) {
        return instant == null ? "N/A" : LocalDate.from(instant.atZone(ZoneId.systemDefault())).toString();
    }
}
