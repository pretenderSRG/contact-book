package org.example.contact_book.service;

import org.example.contact_book.model.Contact;

import java.util.*;
import java.util.stream.Collectors;

public class ContactSearch {
    private ContactManager contactManager;
    private Map<String, Contact> contactStorage;

    public ContactSearch(ContactManager contactManager) {
        this.contactManager = contactManager;
        this.contactStorage = contactManager.getContactStorage();
    }

    public List<Contact> searchByPhoneNumber(String phoneNumber) {
        List<Contact> searchingContacts = contactStorage.entrySet().stream()
                .filter(entry -> entry.getKey().contains(phoneNumber))
                .map(Map.Entry::getValue).collect(Collectors.toList());
        return searchingContacts;
    }

    public List<Contact> searchByName(String name) {
        List<Contact> searchByName = contactStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getName().toLowerCase().contains(name.toLowerCase()))
                .map(Map.Entry::getValue).collect(Collectors.toList());
        return searchByName;
    }

    public List<Contact> searchBySurname(String surname) {
        List<Contact> searchBySurname = contactStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getSurname().toLowerCase().contains(surname.toLowerCase()))
                .map(Map.Entry::getValue).collect(Collectors.toList());
        return searchBySurname;
    }

    public void printSearchResult(List<Contact> searchingResult) {
        if (searchingResult == null || searchingResult.isEmpty()) {
            System.out.println("Нічого не знайдено\n");
        } else {
            for (Contact contact : searchingResult) {
                System.out.println(contact);
                System.out.println();
            }
        }
    }

    public void search(Scanner scanner) {
        System.out.println("=== Меню пошуку ===");
        System.out.println("\t1. Пошук по номеру телефону");
        System.out.println("\t2. Пошук по імені");
        System.out.println("\t3. Пошук по прізвищу");
        System.out.println("\t0. Вийти");
        System.out.print("Ваш вибір: ");

        scanner.nextLine();

        try {
            List<Contact> searchResult = null;
            int userChoice = scanner.nextInt();
            scanner.nextLine();
            switch (userChoice) {
                case 0:
                    break;
                case 1:
                    System.out.println("Введіть номер телелефону (або частину номеру) для пошуку:");
                    String searchingNumber = scanner.nextLine();
                    searchResult = searchByPhoneNumber(searchingNumber);
                    break;
                case 2:
                    System.out.println("Введіть ім'я (або частину імені) для пошуку");
                    String searchingName = scanner.nextLine();
                    searchResult = searchByName(searchingName);
                    break;
                case 3:
                    System.out.println("Введіть прізвище (або частину прізвища) для пошуку");
                    String searchingSurname = scanner.nextLine();
                    searchResult = searchBySurname(searchingSurname);
                    break;
                default:
                    searchResult = new ArrayList<>();
                    System.out.println("Введіть цифру від 0 до 3");
            }
            printSearchResult(searchResult);

        } catch (InputMismatchException e) {
            System.out.println("Не правильний ввід. Введіть цифру 0 - 3");
        }


    }
}
