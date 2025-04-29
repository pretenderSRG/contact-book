package org.example.contact_book2.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {


    @ParameterizedTest
    @ValueSource(strings = {"380509887766", "0509887766"})
    void isPhoneNumberValid_WhenGivenCorrectNumber_ShouldReturnTrue(String number) {

        // assert
        assertTrue(ValidationUtils.isPhoneNumberValid(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"example@mail.com", "io@io.io", "ex-ample@mail.com", "ex1@gmail.com", ""})
    void isEmailValid_WhenGivenCorrectEmail_ShouldReturnTrue(String email) {
        // assertion
        assertTrue(ValidationUtils.isEmailValid(email));
    }

     @ParameterizedTest
    @ValueSource(strings = {"@mail.com", "ioio.io", "ex-a@mple@mail.com", "ex1@gmail"})
    void isEmailValid_WhenGivenIncorrectEmail_ShouldReturnFalse(String email) {
        // assertion
        assertFalse(ValidationUtils.isEmailValid(email));
    }


    @ParameterizedTest
    @ValueSource(strings = {"09887766", "09887766123343", "1212", "0"})
    void isPhoneNumberValid_WhenGivenIncorrectNumber_ShouldReturnFalse(String number) {
        // assert
        assertFalse(ValidationUtils.isPhoneNumberValid(number));
    }

    @Test
    void isPhoneNumberValid_WhenGivenNull_ShouldReturnFalse() {
        // assert
        assertFalse(ValidationUtils.isPhoneNumberValid(null));
    }

    @ParameterizedTest
    @CsvSource({
            "'099 546 44 44', '0995464444'",
            "'050....12345sdfgsder56', 0501234556",
            "'(050) 988-77-66', '0509887766'"
    })
    void normalizePhoneNumber_WhenGivenNumberWithSpaces_ShouldReturnOnlyNumbers(String inputNumber, String expected) {
        assertEquals(expected,ValidationUtils.normalizePhoneNumber(inputNumber));
    }

}