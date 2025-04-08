package org.example.contact_book.service;

import org.example.contact_book.model.Contact;
import org.example.contact_book.util.PhoneUtils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ContactEditor {
    private List<Contact> contactList;
    private ContactManager contactManager;

    public ContactEditor(List<Contact> contactList, ContactManager contactManager) {
        this.contactList = contactList;
        this.contactManager = contactManager;
    }

    private void showEditMenu() {
        System.out.println("--- Меню редагування ---");
        System.out.println("\t1. Редагувати контакт");
        System.out.println("\t2. Видалити контакт");
        System.out.println("\t0. Вийти");
        System.out.print("Ваш вибір: ");
    }

//    public Contact userContactChoice(int indexOfChoiceContact) {
//        try {
//            return contactList.get(indexOfChoiceContact);
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("Користувача з таким порядковим номером не існує");
//        }
//        return null;
//    }

    public void editOrDelete(Scanner scanner) {
        System.out.println("Виберіть контакт для редагування, або 0 для виходу");
        int userIndexChoice = scanner.nextInt();
        if (userIndexChoice == 0) {
            return;
        }

        try {
            Contact editabledContact = contactList.get(userIndexChoice - 1);
            showEditMenu();
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    editContact(editabledContact, scanner);
                    System.out.println("Контакт редаговано успішно");
                    break;

                case 2:
                    deleteContact(editabledContact);
                    System.out.println("Контакт видалено успішно");
                    break;

                case 0:
                    return;
                default:
                    System.out.println("Не вірний вибір. Виберіть 1, 2 або 0");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Користувача з таким порядковим номером не існує");
        } catch (InputMismatchException e) {
            System.out.println("Введіть цифру від 0 до 2");
        }

    }

    private void editContact(Contact contact, Scanner scanner) {

        System.out.println("Введіть дані в поля для оновлення, або залиште пустим щоб не робити змін");
        Contact newContact = new Contact();

        System.out.println("Попередній номер телефону - " + contact.getPhoneNumber());
        System.out.println("\t - Новий номер: ");
        String newNumber = scanner.nextLine();
        newContact.setPhoneNumber(!newNumber.isEmpty() ?
                PhoneUtils.normalizePhoneNumber(newNumber) : contact.getPhoneNumber());

        System.out.println("Попереднє ім'я - " + contact.getName());
        System.out.println("\t - Нове ім'я: ");
        String newName = scanner.nextLine();
        newContact.setName(!newName.isEmpty() ? newName : contact.getName());

        System.out.println("Попереднє прізвище - " + contact.getSurname());
        System.out.println("\t - Нове прізвище: ");
        String newSurname = scanner.nextLine();
        newContact.setSurname(!newSurname.isEmpty() ? newSurname : contact.getSurname());

        System.out.println("Попередній email - " + contact.getEmail());
        System.out.println("\t - Новий email: ");
        String newEmail = scanner.nextLine();
        newContact.setEmail(!newEmail.isEmpty() ? newEmail : contact.getEmail());

        System.out.println("Попередній опис - " + contact.getDescription());
        System.out.println("\t - Новий опис: ");
        String newDescription = scanner.nextLine();
        newContact.setDescription(!newDescription.isEmpty() ? newDescription : contact.getDescription());

        deleteContact(contact);

        contactManager.putContactToBase(newContact);

    }

    public void deleteContact(Contact contact) {
        contactManager.deleteContact(contact);
    }
}