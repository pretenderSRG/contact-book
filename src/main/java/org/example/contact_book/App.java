package org.example.contact_book;

import org.example.contact_book.service.ContactManager;
import org.example.contact_book.service.ContactSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Start applications");
        System.out.println("Проста книга з контактами");

        Scanner scanner = new Scanner(System.in);
        final String pathToContactBase = "src/main/resources/contact_book.bin";
        ContactManager contactManager = new ContactManager(pathToContactBase);

        boolean isRunning = true;

        while (isRunning) {
            showMainMenu();

            try {
                int userChoice = scanner.nextInt();
                scanner.nextLine();
                logger.debug("Користувач вибрав пункт меню: {}",userChoice);


                switch (userChoice) {
                    case 0:
                        System.out.println("Робота програми завершена.");
                        isRunning = false;
                        break;
                    case 1:
                        try {

                            contactManager.createContact(scanner);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            break;
                        }
                    case 2:
                        ContactSearch contactSearch = new ContactSearch(contactManager);
                        contactSearch.search(scanner);
                        break;
                    case 3:
                        contactManager.showAllContacts();
                        break;
                    default:
                        System.out.println("Не правильний ввід. Введіть цифру 0 - 3");
                        logger.warn("Користувач ввів не правильне число ({})", userChoice);
                }
            } catch (InputMismatchException e) {
                System.out.println("Введіть цифру від 0 до 3");
                logger.error("Не віриний ввід, користувач ввів не число ", e);
                scanner.nextLine();
            }
        }
        scanner.close();

    }
    private static void showMainMenu() {
        System.out.println("=== Головне меню ===");
            System.out.println("1. Додати контакт");
            System.out.println("2. Пошук контакту");
            System.out.println("3. Переглянути всі контакти");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");
    }
}