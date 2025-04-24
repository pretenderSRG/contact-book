package org.example.contact_book2;

import org.example.contact_book2.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {

        System.out.println("Контакт Бук 2.0 — SQLite edition \uD83D\uDCD2");

        Scanner scanner = new Scanner(System.in);
        ContactService contactService = new ContactService("jdbc:sqlite:src/main/resources/contact_book.db");

        boolean isRunning = true;
        logger.info("Програму запущено");

        while (isRunning) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> contactService.createContact(scanner);
                case 2 -> contactService.searchContact(scanner);
                case 3 -> contactService.shoeAllContacts();
                case 0 -> {
                    System.out.println("Роботу програми завершено!");
                    isRunning = false;
                }
                default -> System.out.println("Невідомий вибір.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("""
                === Головне меню ===
                1. Додати контакт
                2. Пошук
                3. Показати всі
                0. Вийти
                Ваш вибір: 
                """);
    }
}