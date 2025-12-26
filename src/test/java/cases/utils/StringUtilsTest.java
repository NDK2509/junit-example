package cases.utils;

import base.BaseTestCase;
import org.example.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


public class StringUtilsTest extends BaseTestCase {
    @Test
    public void testIsNullOrEmpty() {
        // Chia lam bao nhieu cases?
        // Case 1: input == null
        assertTrue(StringUtils.isNullOrEmpty(null)); // case 1 => expected: true
        // Case 2: input == ""
        assertTrue(StringUtils.isNullOrEmpty("")); // case 2 => expected: true
        // Case 3: input != "" = "kjhfjfshgjdf"
        assertFalse(StringUtils.isNullOrEmpty("hgjhgjshdvjsvcbvcxbj")); // case 3 => expected: false
        assertFalse(StringUtils.isNullOrEmpty("            "));
        // Da hoan thanh muc tieu cua method => Passed
    }

    @Test
    public void testIsBlank() {
        // case 1: input null => true
        assertTrue(StringUtils.isBlank(null));
        // case 2: input rong => true
        assertTrue(StringUtils.isBlank(""));
        // case 3: input blank
        assertTrue(StringUtils.isBlank("            "));
        // case 4: input not null not blank not empty
        assertFalse(StringUtils.isBlank("hgjhgjshdvjsvcbvcxbj"));
    }

    @Test
    public void testCapitalize() {
        assertNull(StringUtils.capitalize(null));
        assertEquals("", StringUtils.capitalize(""));
        assertEquals("Abcd", StringUtils.capitalize("abcd"));
        assertEquals("Uppercase", StringUtils.capitalize("UPPERCASE"));
        assertEquals("Love You To The Moon", StringUtils.capitalize("love you to the moon"));
        assertEquals("@#$%", StringUtils.capitalize("@#$%"));
        assertEquals("   ", StringUtils.capitalize("   "));
        assertEquals("123", StringUtils.capitalize("123"));
        assertEquals(" Abc", StringUtils.capitalize(" abc"));
        assertEquals("Ă", StringUtils.capitalize("ă"));
        assertEquals("A1b2c3", StringUtils.capitalize("a1b2c3"));
        assertEquals("1a2b3c", StringUtils.capitalize("1a2b3c"));
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "null, null",
                    "'', ''",
                    "abcd, Abcd",
                    "UPPERCASE, Uppercase",
                    "'love you to the moon', 'Love You To The Moon'",
                    "'@#$%', '@#$%'",
                    "'   ', '   '",
                    "123, 123",
                    "' abc', ' Abc'",
                    "ă, Ă",
                    "a1b2c3, A1b2c3",
                    "1a2b3c, 1a2b3c",
                    "😀, 😀"
            }, nullValues = "null")
    void testCapitalize(String input, String expected) {
        assertEquals(expected, StringUtils.capitalize(input));
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "null, null",
                    "abcd, dcba"
            }, nullValues = "null")
    void testReserve(String input, String expected) {
        assertEquals(expected, StringUtils.reverse(input));
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "null, abc, false",
                    "abc, null, false",
                    "null, null, false",
                    "abc, ABCD, true",
                    "abc, XabC, true",
                    "abc, XyZT, false",
                    ".@, kshjdsahg.@, true",
                    "😀, bsjhdgf😀LKFJLKS, true",
                    "Kỳ, Nguyễn Đăng Kỳ, true"
            }, nullValues = "null")
    void testContainsIgnoreCase(String search, String text, boolean expected) {
        assertEquals(expected, StringUtils.containsIgnoreCase(text, search));
    }
}
