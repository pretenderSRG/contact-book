package org.example.contact_book.util;

public class PhoneUtils {

    public static String normalizePhoneNumber(String number) {
        String cleanNumber = number.replaceAll("[^0-9]", "");

        if (cleanNumber.startsWith("0")) {
            cleanNumber = "380" + cleanNumber.substring(1);
        } else if (!cleanNumber.startsWith("380")) {
            cleanNumber = "380" + cleanNumber;
        }
        // перевірка довжини телефонного номера
        if (cleanNumber.length() != 12) {
            throw new IllegalArgumentException("Номер повинен містити 12 цифр, починатися з 0 або +38");
        }

        return String.format("+%s %s %s %s %s",
                cleanNumber.substring(0, 3),  // Код країни
                cleanNumber.substring(3, 5), // Код оператора
                cleanNumber.substring(5, 8), // Перша частина номера
                cleanNumber.substring(8, 10),// Друга частина номера
                cleanNumber.substring(10, 12));
    }
}
