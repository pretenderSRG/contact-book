package org.example.contact_book;

import org.example.contact_book.util.PhoneUtils;

public class AppLoggerTest {
    public static void main(String[] args) {
        try {
            System.out.println("---Перевірка ловування---");
            String phoneNumber = PhoneUtils.normalizePhoneNumber("090-988-6676");
            System.out.println("Формвтований номер виглядає: " + phoneNumber);
        }catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }

        try {
            PhoneUtils.normalizePhoneNumber("123");
        } catch (IllegalArgumentException e) {
            System.err.println("Очікувана помилка: " + e.getMessage());
        }
    }
}
