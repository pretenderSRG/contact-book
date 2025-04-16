package org.example.contact_book.util;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PhoneUtilsTest {
    @Test
    public void normalizePhoneNumber_ValidNumberStartingWith0() {
        String input = "0892773344";
        String expected = "+380 89 277 33 44";
        assertEquals(expected, PhoneUtils.normalizePhoneNumber(input));
    }

    @Test
    public void normalizePhoneNumber_ValidNumberStartingWith380() {
        String input = "380892773344";
        String expected = "+380 89 277 33 44";
        assertEquals(expected, PhoneUtils.normalizePhoneNumber(input));
    }

    @Test
    public void normalizePhoneNumber_InvalidLength() {
        String input = "1234";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PhoneUtils.normalizePhoneNumber(input);
        });
        assertTrue(exception.getMessage().contains("Номер повинен містити 12 цифр, починатися з 0 або +38"));
    }

    @Test
    public void normalizePhoneNumber_ValidNumberWithDashAndSpace() {
        String input = "050-066 55 85";
        String expected = "+380 50 066 55 85";
        assertEquals(expected, PhoneUtils.normalizePhoneNumber(input));
    }

    @Test
    public void normalizePhoneNumber_InvalidNumberStart() {
        String input = "180 099 88 76";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {});
        assertTrue(exception.getMessage().contains("Номер повинен містити 12 цифр, починатися з 0 або +38"));
    }
}
