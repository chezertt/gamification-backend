package com.gamification.gamificationbackend.util;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {

    private DateTimeUtil() {}

    public static OffsetDateTime nowInUTC() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDate currentDateInUTC() {
        return LocalDate.now(ZoneOffset.UTC);
    }

    public static String format(OffsetDateTime dateTime) {
        return dateTime.plusHours(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " МСК";
    }

    public static String format(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM-dd"));
    }
}
