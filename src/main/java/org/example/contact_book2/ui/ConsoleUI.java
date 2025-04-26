package org.example.contact_book2.ui;

import org.example.contact_book2.model.Contact;
import org.example.contact_book2.service.ContactService;
import org.example.contact_book2.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleUI {
    private final static Logger logger = LoggerFactory.getLogger(ConsoleUI.class);
    private final ContactService contactService;
    private final Scanner scanner;

    public ConsoleUI(ContactService contactService) {
        this.contactService = contactService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            showMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addContact();
                case "2" -> showAllContacts();
                case "3" -> searchMenu();
                case "0" -> {
                    System.out.println("Роботу програми заваершено");
                    logger.info("Роботу програми завершено");
                    return;
                }
                default -> System.out.println("Не вірний вибір");
            }

        }
    }

    private void showMenu() {
        System.out.println("""
                === Головне меню ===
                1. Додати контакт
                2. Показати всі
                3. Пошук
                0. Вийти
                Ваш вибір: 
                """);
    }

    private void addContact() {
        System.out.println("Ім'я: ");
        String name = scanner.nextLine();

        System.out.println("Прізвище: ");
        String surname = scanner.nextLine();

        String phone;
        while (true) {
            System.out.println("Номер телефону: ");
            phone = ValidationUtils.normalizePhoneNumber(scanner.nextLine());
            if (ValidationUtils.isPhoneNumberValid(phone)) {
                break;
            } else {
                System.out.println("Не привильний номер! Спробуйте ше раз.");
            }
        }

        System.out.println("Email: ");
        String email;
        while (true) {
            email = scanner.nextLine();
            if (ValidationUtils.isEmailValid(email)) {
                break;
            } else {
                System.out.println("Невірний формат email. Спробуйте ще раз.");
            }
        }

        System.out.println("Опис: ");
        String description = scanner.nextLine();

        Contact contact = new Contact(name, surname, phone, email, description);
        contactService.addContact(contact);

        // Перевіряємо, чи ID було встановлено після додавання
        if (contact.getId() > 0) {
            logger.info("Контакт додано з ID: {}" ,contact.getId());
        } else {
            System.out.println("Контакт додано, але ID не встановлено. Перевірте базу даних.");
            logger.warn("Контакт додано без ID: {}", contact);
        }

        System.out.println("Контакт додано");
    }

    private void showAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();

        if (contacts.isEmpty()) {
            System.out.println("Контактів не знайдено");
        } else {
            AtomicInteger counter = new AtomicInteger(1);
            for (Contact contact : contacts) {
                System.out.println(counter.getAndIncrement() + ") " + contact);
            }
        }
    }

    private void searchMenu() {
        System.out.println("Пошук за:");
        System.out.println("1. Ім'ям");
        System.out.println("2. Прізвищем");
        System.out.println("3. Телефоном");
        System.out.println("4. Email");
        System.out.print("Ваш вибір: ");
        String option = scanner.nextLine();

        System.out.println("Введіть пошукове значення: ");
        String value = scanner.nextLine();

        List<Contact> result = switch (option) {
            case "1" -> contactService.searchByName(value);
            case "2" -> contactService.searchBySurname(value);
            case "3" -> contactService.searchByPhone(value);
            case "4" -> contactService.searchByEmail(value);
            default -> {
                System.out.println("Не вірний вибір");
                yield List.of();
            }
        };

        if (result.isEmpty()) {
            System.out.println("Контактів не знайдено");
            return;
        } else {
            AtomicInteger counter = new AtomicInteger(1);
            for (Contact contact : result) {
                System.out.println(counter.getAndIncrement() + ") " + contact);
            }
        }

        System.out.print("Виберіть номер контакту для дій або 0 щоб повернутись: ");
        String input = scanner.nextLine();

        int index;
        try {
            index = Integer.parseInt(input);
            if (index == 0) return;
            if (index < 0 || index > result.size()) {
                System.out.println("Невірний номер");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("введено не число");
            return;
        }
        Contact selectedContact = result.get(index - 1);

        System.out.println("Оберіть дію:");
        System.out.println("1. Редагувати");
        System.out.println("2. Видалити");
        System.out.println("0. Назад");

        String action = scanner.nextLine();

        switch (action) {
            case "1" -> updateContact(selectedContact);
            case "2" -> deleteContact(selectedContact);
            case "0" -> {
                return;
            }
            default -> System.out.println("Невірна дія");
        }


    }

    private void deleteContact(Contact selectedContact) {
        contactService.deleteContactByPhone(selectedContact.getPhoneNumber());
        System.out.println("Контакт видалено");
    }

    private void updateContact(Contact selectedContact) {
        System.out.println("Редагування контакту (залиште порожнім, щоб не змінювати):");

        System.out.print("Нове ім'я (" + selectedContact.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) {
            selectedContact.setName(name);
        }

        System.out.print("Нове прізвище (" + selectedContact.getSurname() + "): ");
        String surname = scanner.nextLine();
        if (!surname.isBlank()) {
            selectedContact.setSurname(surname);
        }

        while (true) {
            System.out.print("Новий телефон (" + selectedContact.getPhoneNumber() + "): ");
            String phone = scanner.nextLine();
            if (phone.isBlank()) break;
            phone = ValidationUtils.normalizePhoneNumber(phone);
            if (ValidationUtils.isPhoneNumberValid(phone)) {
                selectedContact.setPhoneNumber(phone);
                break;
            } else {
                System.out.println("Не вірний номер телефону.");
            }
        }

        while (true) {
            System.out.print("Новий email (" + selectedContact.getEmail() + "): ");
            String email = scanner.nextLine();
            if (email.isBlank()) break;
            if (ValidationUtils.isEmailValid(email)) {
                selectedContact.setEmail(email);
                break;
            } else {
                System.out.println("Не вірний email");
            }
        }

        System.out.print("Новий опис (" + selectedContact.getDescription() + "): ");
        String description = scanner.nextLine();
        if (!description.isBlank()) {
            selectedContact.setDescription(description);
        }
        /// перевірка ID
        if (selectedContact.getId() == 0) {
            System.out.println("Помилка: у вибраного контакту відсутній ID.");
            logger.error("ID контакту не встановлено: {}", selectedContact);
            return;
        }


        contactService.updateContact(selectedContact);
        System.out.println("Контакт оновлено.");


    }
}
