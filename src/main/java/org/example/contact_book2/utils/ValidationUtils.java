package org.example.contact_book2.utils;

public class ValidationUtils {

    private final static String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
;
    private final static String phoneRegex = "\\d{10}|\\d{12}";

    public static boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) return false;
        return phoneNumber.matches(phoneRegex);
    }

    public static boolean isEmailValid(String contactEmail) {
        if (contactEmail == null) return false;
        return contactEmail != null && contactEmail.isEmpty() || contactEmail.matches(emailRegex);
    }

    public static String normalizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("\\D", "");
    }
}
