package org.example.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DatetimeFormatterUtils {
    public static final String JUST_NOW = "just now";
    public static final String MINUTES_AGO = " minutes ago";
    public static final String HOURS_AGO = " hours ago";
    public static final String YESTERDAY = "yesterday";
    public static final String DAYS_AGO = " days ago";
    public static final String MONTHS_AGO = " months ago";
    public static final String YEARS_AGO = " years ago";

    private DatetimeFormatterUtils() {
    }

    // List of common formats you may want to parse
    private static final List<DateTimeFormatter> COMMON_FORMATS = List.of(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,
            DateTimeFormatter.RFC_1123_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
    );

    /**
     * Tries parsing a date using multiple formats.
     */
    public static LocalDateTime parseFlexible(String input) {
        if (input == null) return null;

        for (DateTimeFormatter fmt : COMMON_FORMATS) {
            try {
                return LocalDateTime.parse(input, fmt);
            } catch (DateTimeParseException ignore) {
            }
            try {
                return ZonedDateTime.parse(input, fmt).toLocalDateTime();
            } catch (Exception ignore) {
            }
        }

        throw new IllegalArgumentException("Unrecognized datetime format: " + input);
    }

    /**
     * Converts date/time between timezones.
     */
    public static String convertTimezone(
            String datetime,
            ZoneId from,
            ZoneId to,
            String outputPattern
    ) {
        LocalDateTime parsed = parseFlexible(datetime);
        ZonedDateTime zdt = parsed.atZone(from).withZoneSameInstant(to);
        return zdt.format(DateTimeFormatter.ofPattern(outputPattern));
    }

    /**
     * Returns "2 days ago", "5 minutes ago", "just now" style strings.
     */
    public static String toHumanReadableDiff(LocalDateTime target) {
        if (target == null) return null;

        Duration diff = Duration.between(target, LocalDateTime.now());
        long seconds = diff.getSeconds();

        if (seconds < 0) return null;
        if (seconds < 60) return JUST_NOW;
        if (seconds < 3600) return (seconds / 60) + MINUTES_AGO;
        if (seconds < 86_400) return (seconds / 3600) + HOURS_AGO;
        if (seconds < 2 * 86_400) return YESTERDAY;
        if (seconds < 30L * 86_400) return (seconds / 86_400) + DAYS_AGO;
        if (seconds < 365L * 86_400) return (seconds / (30L * 86_400)) + MONTHS_AGO;

        return (seconds / (365L * 86_400)) + YEARS_AGO;
    }

    /**
     * Returns the start of week (Monday).
     */
    public static LocalDate getStartOfWeek(LocalDate input) {
        return input.with(DayOfWeek.MONDAY);
    }

    /**
     * Formats a timestamp into ISO string with milliseconds.
     */
    public static String formatIsoWithMillis(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }

    /**
     * Adds or subtracts time using a compact pattern like "1d", "-3h", "45m".
     */
    public static LocalDateTime shiftBy(String pattern, LocalDateTime base) {
        if (pattern == null || base == null) return base;

        char unit = pattern.charAt(pattern.length() - 1);
        long value = Long.parseLong(pattern.substring(0, pattern.length() - 1));

        return switch (unit) {
            case 'd' -> base.plusDays(value);
            case 'h' -> base.plusHours(value);
            case 'm' -> base.plusMinutes(value);
            case 's' -> base.plusSeconds(value);
            default -> throw new IllegalArgumentException("Unknown time unit: " + unit);
        };
    }
}
