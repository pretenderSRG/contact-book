package org.example.contact_book2.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void isPhoneNumberValid_WhenGivenCorrectNumber_ShouldReturnTrue() {
        // arrange
        String number = "380509887766";

        // assert
        assertTrue(ValidationUtils.isPhoneNumberValid(number));
    }

    @Test
    void isPhoneNumberValid_WhenGivenShortCorrectNumber_ShouldReturnTrue() {
        // arrange
        String number = "0509887766";

        // assert
        assertTrue(ValidationUtils.isPhoneNumberValid(number));
    }

    @Test
    void isEmailValid() {
    }

     @Test
    void isPhoneNumberValid_WhenGivenShortIncorrectNumber_ShouldReturnFalse() {
        // arrange
        String number = "09887766";

        // assert
        assertFalse(ValidationUtils.isPhoneNumberValid(number));
    }

    @Test
    void normalizePhoneNumber() {
    }
}