package org.example.contact_book.util;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PhoneUtilsTest {
    @Test
    public void testNormalizeValidNumberStartingWith0() {
        String input = "0892773344";
        String expected = "+380 89 277 33 44";
        assertEquals(expected, PhoneUtils.normalizePhoneNumber(input));
    }

    @Test
    public void testNormalizeValidNumberStartingWith380() {
        String input = "380892773344";
        String expected = "+380 89 277 33 44";
        assertEquals(expected, PhoneUtils.normalizePhoneNumber(input));
    }

    @Test
    public void testNormalizeInvalidLength() {
        String input = "1234";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PhoneUtils.normalizePhoneNumber(input);
        });
        assertTrue(exception.getMessage().contains("Номер повинен містити 12 цифр, починатися з 0 або +38"));
    }
}
