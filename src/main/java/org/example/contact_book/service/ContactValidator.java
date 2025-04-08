package org.example.contact_book.service;

public class ContactValidator {

    public static boolean isPhoneNumberValid(String phoneNumber) {
        String phoneRegex = "\\+380\\s?\\d{2}\\s?\\d{3}\\s?\\d{2}\\s?\\d{2}";
        if (phoneNumber == null || !phoneNumber.matches(phoneRegex)) {
            System.out.println("Номер недійсний. Введіть у форматі +380 XX XXX XX XX");
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String contactEmail) {
        return contactEmail.matches("\\w+@\\w+\\.[a-zA-Z]{2,6}") || contactEmail.isEmpty();
    }
}
