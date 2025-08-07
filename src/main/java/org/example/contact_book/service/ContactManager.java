package org.example.contact_book.service;

import org.example.contact_book.model.Contact;
import org.example.contact_book.util.FileStorageService;
import org.example.contact_book.util.Loggable;
import org.example.contact_book.util.PhoneUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactManager implements Loggable {
    private FileStorageService fileStorage;
    private Map<String, Contact> contactStorage;

    public ContactManager(String pathToContactBase) {
        fileStorage = new FileStorageService(pathToContactBase);
        contactStorage = fileStorage.readDataFromFile();
    }

    public  ContactManager(String pathToContactBase, FileStorageService fileStorageService) {
        this.fileStorage = fileStorageService;
        this.contactStorage = fileStorage.readDataFromFile();
    }

    public boolean putContactToBase(Contact contact) {
        String contactPhoneNumber = contact.getPhoneNumber();

        if (!ContactValidator.isPhoneNumberValid(contactPhoneNumber)) {
            logger().debug("Не правильний номер телефону");
            System.out.println("Номер телефону введено неправильно");
            return false;
        } else if (!ContactValidator.isEmailValid(contact.getEmail())) {
            logger().debug("Не правильний email");
            System.out.println("Email введено неправильно");
            return false;
        } else if (contactStorage.containsKey(contactPhoneNumber)) {
            logger().debug("Номер телефону вже є в базі");
            System.out.println("Контакт з таким номером телефону існує");
            return false;
        } else {
            contactStorage.put(contactPhoneNumber, contact);
            logger().info("Додано контакт:");
            System.out.println("Контакт створено успішо");
            System.out.println(contact);
            fileStorage.writeDataToFile(contactStorage);
            return true;
        }
    }

    public void createContact(Scanner scanner) {
        System.out.println("Введіть дані для створення нового контакту:");

        System.out.print("\tВведіть номер телефону: ");
//        scanner.nextLine();
        String inputLine = scanner.nextLine();
        while (inputLine.isBlank()) inputLine = scanner.nextLine();

        String phoneNumber = PhoneUtils.normalizePhoneNumber(inputLine);

        System.out.print("\tВведіть ім'я: ");
        String name = scanner.nextLine();

        System.out.print("\tВведіть прізвище: ");
        String surname = scanner.nextLine();

        System.out.print("\tВведіть email: ");
        String email = scanner.nextLine();
        if (email.isEmpty()) {
            logger().info("Email is empty");
            email = "NO_email@email.com";
            logger().info("Email порожній");
        }

        System.out.print("\tВведіть короткий опис контакту: ");
        String description = scanner.nextLine();

        Contact newContact = new Contact(name, surname, phoneNumber, email, description);
        if (contactStorage.containsKey(newContact.getPhoneNumber())) {
            logger().warn("Контакт з таким номером вже існує");
            return;
        }

        logger().info("Контакт додано в базу: {} {} - {}",
                newContact.getPhoneNumber(),newContact.getSurname(), newContact.getPhoneNumber());
        putContactToBase(newContact);

    }

    public void deleteContact(Contact contact) {
        String contactPhoneNumber = contact.getPhoneNumber();
        if (contactStorage.containsKey(contactPhoneNumber)) {
            contactStorage.remove(contact.getPhoneNumber());
            fileStorage.writeDataToFile(contactStorage);
            logger().info("Контакт видалено: {} {} - {}",
                    contact.getPhoneNumber(), contact.getName(), contact.getSurname());
        } else {
            logger().warn("Контакта {} {} - {} не має в базі",
                    contact.getPhoneNumber(), contact.getName(), contact.getSurname());
        }
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
