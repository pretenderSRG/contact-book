package org.example.contact_book.service;

import org.example.contact_book.model.Contact;
import org.example.contact_book.util.FileStorageService;
import org.example.contact_book.util.PhoneUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactManager {
    private FileStorageService fileStorage;
    private Map<String, Contact> contactStorage;

    public ContactManager(String pathToContactBase) {
        fileStorage = new FileStorageService(pathToContactBase);
        contactStorage = fileStorage.readDataFromFile();
    }

    public void putContactToBase(Contact contact) {
        String contactPhoneNumber = contact.getPhoneNumber();

        if (!ContactValidator.isPhoneNumberValid(contactPhoneNumber)) {
            System.out.println("Не правильний номер телефону");
        } else if (!ContactValidator.isEmailValid(contact.getEmail())) {
            System.out.println("Не правильний email");
        } else {
            contactStorage.put(contactPhoneNumber, contact);
            System.out.println("Додано контакт:");
            System.out.println(contact);
            fileStorage.writeDataToFile(contactStorage);
        }
    }

    public void createContact(Scanner scanner) {
        System.out.println("Введіть дані для створення нового контакту:");

        System.out.print("\tВведіть номер телефону: ");
        scanner.nextLine();
        String phoneNumber = PhoneUtils.normalizePhoneNumber(scanner.nextLine());

        System.out.print("\tВведіть ім'я: ");
        String name = scanner.nextLine();

        System.out.print("\tВведіть прізвище: ");
        String surname = scanner.nextLine();

        System.out.print("\tВведіть email: ");
        String email = scanner.nextLine();
        if (email.isEmpty()) {
            email = "no_email@email.com";
        }

        System.out.print("\tВведіть короткий опис контакту: ");
        String description = scanner.nextLine();

        Contact newContact = new Contact(name, surname, phoneNumber, email, description);
        if (contactStorage.containsKey(newContact.getPhoneNumber())) {
            System.out.println("Контакт з таким номером вже існує");
            return;
        }

        putContactToBase(newContact);

    }

    public void deleteContact(Contact contact) {
        contactStorage.remove(contact.getPhoneNumber());
        fileStorage.writeDataToFile(contactStorage);
    }

    public void showAllContacts() {
        if (contactStorage.isEmpty()) {
            System.out.println("Список контактів порожній");
        } else {
            AtomicInteger counter = new AtomicInteger(1);
            contactStorage.values().stream().sorted(Comparator.comparing((Contact contact) -> contact.getName().toLowerCase())
                            .thenComparing(contact -> contact.getSurname().toLowerCase()))
                    .forEach(contact -> System.out.printf("%d) %s\n", counter.getAndIncrement(), contact));

        }

    }

    public Map<String, Contact> getContactStorage() {
        return contactStorage;
    }
}
