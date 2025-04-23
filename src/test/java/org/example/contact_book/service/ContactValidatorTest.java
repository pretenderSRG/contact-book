package org.example.contact_book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactValidatorTest {

    @Test
    @DisplayName("isPhoneNumberValid - phone number is valid")
    void isPhoneNumberValid_NumberIsFullCorrect() {
        // arrange
        String correctNumber = "+380 50 123 34 56";

        // assert
        assertTrue(ContactValidator.isPhoneNumberValid(correctNumber));
    }

    @Test
    @DisplayName("isPhoneNumberValid - number without spase")
    void isPhoneNumberValid_NumberIsCorrectWithoutSpace() {
        // arrange
        String correctNumber = "+380501233456";

        // assert
        assertTrue(ContactValidator.isPhoneNumberValid(correctNumber));
    }

    @Test
    @DisplayName("isPhoneNumberValid - number is invalid")
    void isPhoneNumberValid_NumberIsInvalid() {
        // arrange
        String incorrectNumber = "80501233456";

        // assert
        assertFalse(ContactValidator.isPhoneNumberValid(incorrectNumber));
    }

     @Test
    @DisplayName("isPhoneNumberValid - null input return false")
    void isPhoneNumberValid_NullInput() {
        // assert
        assertFalse(ContactValidator.isPhoneNumberValid(null));
    }

    @Test
    @DisplayName("isEmailValid - correct email")
    void isEmailValid_CorrectEmail() {
        // arrange
        String correctEmail = "correct@email.com";

        // assert
        assertTrue(ContactValidator.isEmailValid(correctEmail));
    }

    @Test
    @DisplayName("isEmailValid - incorrect email without @")
    void isEmailValid_IncorrectEmailWithoutA() {
        // arrange
        String correctEmail = "incorrect.email.com";

        // assert
        assertFalse(ContactValidator.isEmailValid(correctEmail));
    }

    @Test
    @DisplayName("isEmailValid - incorrect email without domain")
    void isEmailValid_IncorrectEmailWithoutDomain() {
        // arrange
        String correctEmail = "incorrect@email";

        // assert
        assertFalse(ContactValidator.isEmailValid(correctEmail));
    }

    @Test
    @DisplayName("isEmailValid - incorrect email without name")
    void isEmailValid_IncorrectEmailWithoutName() {
        // arrange
        String correctEmail = "@email.com";

        // assert
        assertFalse(ContactValidator.isEmailValid(correctEmail));
    }

    @Test
    @DisplayName("isEmailValid - incorrect email, null input")
    void isEmailValid_NullInput() {
        // assert
        assertFalse(ContactValidator.isEmailValid(null));
    }

        @Test
    @DisplayName("isEmailValid - empty string return true")
    void isEmailValid_EmptyEmail() {
        // arrange
        String correctEmail = "";

        // assert
        assertTrue(ContactValidator.isEmailValid(correctEmail));
    }
}