package org.example.contact_book.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactValidator {
    private final  static Logger logger = LoggerFactory.getLogger(ContactValidator.class);
    private final static String phoneRegex = "\\+380\\s?\\d{2}\\s?\\d{3}\\s?\\d{2}\\s?\\d{2}";
    private final static String emailRegex = "\\w+@\\w+\\.[a-zA-Z]{2,6}";

    public static boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches(phoneRegex)) {
            logger.error("Номер недійсний. Введіть у форматі +380 XX XXX XX XX");
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String contactEmail) {
        if (contactEmail == null) return false;
        return contactEmail.matches(emailRegex) || contactEmail.isEmpty();
    }
}
