package cases.utils;

import base.BaseTestCase;
import org.example.utils.DatetimeFormatterUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;


public class DatetimeFormatterUtilsTest extends BaseTestCase {
    private static final String NOW_STR = "2025-12-26T19:40:00";
    private static final LocalDateTime NOW = LocalDateTime.parse(NOW_STR);
    private static final LocalDateTime FUTURE = NOW.plusDays(1);

    @Test
    void testToHumanReadable_shouldReturn_null() {
        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            assertNull(DatetimeFormatterUtils.toHumanReadableDiff(null));
        }
    }

    @Test
    void testToHumanReadable_shouldReturn_justNow() {
        var testDatetime = NOW.minusSeconds(1);

        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            assertEquals(DatetimeFormatterUtils.JUST_NOW, DatetimeFormatterUtils.toHumanReadableDiff(testDatetime));
        }
    }

    @Test
    void testToHumanReadable_shouldReturn_minutesAgo() {
        var expectedNowDiff = 2;
        var testDatetime = NOW.minusMinutes(expectedNowDiff);

        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            var expected = expectedNowDiff + DatetimeFormatterUtils.MINUTES_AGO;
            assertEquals(expected, DatetimeFormatterUtils.toHumanReadableDiff(testDatetime));
        }
    }

    @Test
    void testToHumanReadable_shouldReturn_hoursAgo() {
        var expectedNowDiff = 2;
        var testDatetime = NOW.minusHours(expectedNowDiff);

        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            var expected = expectedNowDiff + DatetimeFormatterUtils.HOURS_AGO;
            assertEquals(expected, DatetimeFormatterUtils.toHumanReadableDiff(testDatetime));
        }
    }

    @Test
    void testToHumanReadable_shouldReturn_yesterday() {
        var testDatetime = NOW.minusDays(1);

        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            assertEquals(DatetimeFormatterUtils.YESTERDAY, DatetimeFormatterUtils.toHumanReadableDiff(testDatetime));
        }
    }

    @Test
    void testToHumanReadable_shouldReturn_daysAgo() {
        var expectedNowDiff = 5;
        var testDatetime = NOW.minusDays(expectedNowDiff);

        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            var expected = expectedNowDiff + DatetimeFormatterUtils.DAYS_AGO;
            assertEquals(expected, DatetimeFormatterUtils.toHumanReadableDiff(testDatetime));
        }
    }

    @Test
    void testToHumanReadable_shouldReturn_monthsAgo() {
        var expectedNowDiff = 5;
        var testDatetime = NOW.minusMonths(expectedNowDiff);

        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            var expected = expectedNowDiff + DatetimeFormatterUtils.MONTHS_AGO;
            assertEquals(expected, DatetimeFormatterUtils.toHumanReadableDiff(testDatetime));
        }
    }

    @Test
    void testToHumanReadable_shouldReturn_yearsAgo() {
        var expectedNowDiff = 5;
        var testDatetime = NOW.minusYears(expectedNowDiff);

        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            var expected = expectedNowDiff + DatetimeFormatterUtils.YEARS_AGO;
            assertEquals(expected, DatetimeFormatterUtils.toHumanReadableDiff(testDatetime));
        }
    }

    @Test
    void testToHumanReadable_futureDateTime() {
        try (MockedStatic<LocalDateTime> mocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS)) {
            mocked.when(LocalDateTime::now).thenReturn(NOW);
            assertNull(DatetimeFormatterUtils.toHumanReadableDiff(FUTURE));
        }
    }
}
