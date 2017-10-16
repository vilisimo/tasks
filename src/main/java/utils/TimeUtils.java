package utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TimeUtils {

    public static String instantLocalDate(Instant instant) {
        return instant == null ? "N/A" : LocalDate.from(instant.atZone(ZoneId.systemDefault())).toString();
    }
}
