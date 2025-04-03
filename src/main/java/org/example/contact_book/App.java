package org.example.contact_book;

import org.example.contact_book.model.Contact;
import org.example.contact_book.service.ContactManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Scanner scanner = new Scanner(System.in);
        final String pathToContactBase = "src/main/resources/contact_book.bin";
        ContactManager contactManager = new ContactManager(pathToContactBase);

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("=== Головне меню ===");
            System.out.println("1. Додати контакт");
            System.out.println("2. Пошук контакту");
            System.out.println("3. Переглянути всі контакти");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");

            try {
                int userChoice = scanner.nextInt();

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
                        Contact searchingContact = new Contact();
                        System.out.println("Search");
                        break;
                    case 3:
                        contactManager.showAllContacts();
                        break;
                    default:
                        System.out.println("Не правильний ввід. Введіть цифру 0 - 3");
                }
            }catch (InputMismatchException e) {
                System.out.println("Введіть цифру від 0 до 3");
                scanner.nextLine();
            }


        }
        scanner.close();

    }
}