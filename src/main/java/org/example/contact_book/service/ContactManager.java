package org.example.contact_book.service;

import org.example.contact_book.model.Contact;
import org.example.contact_book.util.FileStorageService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactManager {
    private FileStorageService fileStorage;
    private Map<String, Contact> contactStorage;

    public ContactManager(String pathToContactBase) {
        fileStorage = new FileStorageService(pathToContactBase);
        contactStorage = fileStorage.readDataFromFile();
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        String phoneRegex = "\\+380\\s?\\d{2}\\s?\\d{3}\\s?\\d{2}\\s?\\d{2}";
        if (phoneNumber == null || !phoneNumber.matches(phoneRegex)) {
            System.out.println("Номер недійсний. Введіть у форматі +380 XX XXX XX XX");
            return false;
        }
        return true;
    }

    private String normalizePhoneNumber(String number) {
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

    private boolean isEmailValid(String contactEmail) {
        return contactEmail.matches("\\w+@\\w+\\.[a-zA-Z]{2,6}");
    }

    private void putContactToBase(Contact contact) {
        String contactPhoneNumber = contact.getPhoneNumber();
        if (contactStorage.containsKey(contactPhoneNumber)) {
            System.out.println("Контакт з таким номером вже існує");
        } else {
            if (isPhoneNumberValid(contactPhoneNumber) && isEmailValid(contact.getEmail())) {
                contactStorage.put(contactPhoneNumber, contact);
                System.out.println("Додано контакт:");
                System.out.println(contact);
            } else {
                System.out.println("Не правильний номер телефону обо email");
            }
        }
    }

    public void createContact(Scanner scanner) {
        System.out.println("Введіть дані для створення нового контакту:");

        System.out.print("\tВведіть ім'я: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("\tВведіть прізвище: ");
        String surname = scanner.nextLine();

        System.out.print("\tВведіть номер телефону: ");
        String phoneNumber = normalizePhoneNumber(scanner.nextLine());

        System.out.print("\tВведіть email: ");
        String email = scanner.nextLine();

        System.out.print("\tВведіть короткий опис контакту: ");
        String description = scanner.nextLine();

        Contact newContact = new Contact(name, surname, phoneNumber, email, description);

        putContactToBase(newContact);
        fileStorage.writeDataToFile(contactStorage);

    }

    public void showAllContacts() {
        if (contactStorage.isEmpty()) {
            System.out.println("Список контактів порожній");
        } else {
            AtomicInteger counter = new AtomicInteger(1);
            contactStorage.values().stream().sorted(Comparator.comparing((Contact contact) -> contact.getName().toLowerCase())
                            .thenComparing(contact -> contact.getSurname().toLowerCase()))
                    .forEach(contact -> System.out.printf("%d) %s\n",counter.getAndIncrement(), contact));

        }

    }

    public Map<String, Contact> getContactStorage() {
        return contactStorage;
    }
}
