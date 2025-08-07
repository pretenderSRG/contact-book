package org.example.contact_book.service;

import org.example.contact_book.model.Contact;
import org.example.contact_book.util.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContactManagerTest {
    private ContactManager contactManager;
    private FileStorageService mockFileStorage;
    private Map<String, Contact> mockContactStorage;

    @BeforeEach
    void setUp() {
        mockFileStorage = mock(FileStorageService.class);
        mockContactStorage = new HashMap<>();

        when(mockFileStorage.readDataFromFile()).thenReturn(mockContactStorage);

        contactManager = new ContactManager("mockPath", mockFileStorage);

    }

    @Test
    void putContactToBase_ValidContact() {
        Contact validContact = new Contact("Іван", "Мельник",
                "+380987654321", "ivan@example.com", "Опис");

        contactManager.putContactToBase(validContact);

        assertTrue(contactManager.getContactStorage().containsKey(validContact.getPhoneNumber()));
        assertEquals(validContact, contactManager.getContactStorage().get(validContact.getPhoneNumber()));
        verify(mockFileStorage, times(1)).writeDataToFile(mockContactStorage);
    }

    @Test
    void putContactToBase_InvalidPhoneNumber() {
        Contact invalidContact = new Contact("Іван", "Мельник",
                "1111", "ivan@example.com", "Опис");

        contactManager.putContactToBase(invalidContact);

        assertFalse(contactManager.getContactStorage().containsKey(invalidContact), invalidContact.getPhoneNumber());
        verify(mockFileStorage, never()).writeDataToFile(mockContactStorage);
    }

    @Test
    void putContactToBase_InvalidEmail() {
        Contact contact = new Contact("Іван", "Мельник",
                "+380987654321", "iexample.com", "Опис");

        assertFalse(contactManager.putContactToBase(contact));
        verify(mockFileStorage, never()).writeDataToFile(mockContactStorage);
    }

    @Test
    void deleteContact_ContactInBase() {
        Contact contact = new Contact("Іван", "Мельник",
                "+380 99 876 55 44", "ivan@example.com", "Опис");

        contactManager.putContactToBase(contact);

        contactManager.deleteContact(contact);

        assertFalse(contactManager.getContactStorage().containsKey(contact.getPhoneNumber()));
        verify(mockFileStorage, times(2)).writeDataToFile(mockContactStorage);
    }

    @Test
    void deleteContact_ContactNotInBase() {
        Contact contact = new Contact("Іван", "Мельник",
                "1111", "ivan@example.com", "Опис");

        contactManager.deleteContact(contact);

        assertFalse(contactManager.getContactStorage().containsKey(contact.getPhoneNumber()));
        verify(mockFileStorage, never()).writeDataToFile(mockContactStorage);
    }

    @Test
    void createContact_WithValidData() {
        String simulatedInput = "+380111111111\nІван\nМельник\nivan@example.com\nОпис";
        ByteArrayInputStream inputData = new ByteArrayInputStream(simulatedInput.getBytes());

        Scanner scanner = new Scanner(inputData);

        contactManager.createContact(scanner);

        assertTrue(contactManager.getContactStorage().containsKey("+380111111111"));

        Contact createdContact = contactManager.getContactStorage().get("+380111111111");
        assertEquals("Іван", createdContact.getName());
        assertEquals("Мельник", createdContact.getSurname());
        assertEquals("+380111111111", createdContact.getPhoneNumber());
        assertEquals("ivan@example.com", createdContact.getEmail());
        assertEquals("Опис", createdContact.getDescription());
        verify(mockFileStorage, times(1)).writeDataToFile(mockContactStorage);
    }

    @Test
    void createContact_WithInvalidPhoneNumber() {
        String simulatedInput = "111111\nІван\nМельник\nivan@example.com\nОпис";
        ByteArrayInputStream inputData = new ByteArrayInputStream(simulatedInput.getBytes());

        Scanner scanner = new Scanner(inputData);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {

            contactManager.createContact(scanner);
        });

        assertTrue(exception.getMessage().contains("Номер повинен містити 12 цифр, починатися з 0 або +38"));
    }

    @Test
    void showAllContacts_WhenContactsInBase() {
        Contact contact1 = new Contact("Іван", "Мельник",
                "+380987654321", "ivan@example.com", "Опис");
        Contact contact2 = new Contact("Петро", "Іваненко",
                "+380909887777", "prtro@example.com", "Опис1");
        Contact contact3 = new Contact("Марія", "Ткач",
                "+380661111111", "mary@example.com", "Опис2");

        contactManager.putContactToBase(contact1);
        contactManager.putContactToBase(contact2);
        contactManager.putContactToBase(contact3);

        assertEquals(3, contactManager.getContactStorage().size());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));
        contactManager.showAllContacts();

        System.setOut(System.out);

        String output = outContent.toString();

        assertTrue(output.contains("Іван"));
        assertTrue(output.contains("Мельник"));
        assertTrue(output.contains("Марія"));
        assertTrue(output.contains("+380909887777"));
        assertTrue(output.contains("mary@example.com"));
    }

    @Test
    void showAllContacts_WhenNoContactsInBase() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        contactManager.showAllContacts();
        System.setOut(System.out);

        String output = outputStream.toString();

        assertTrue(output.contains("Список контактів порожній"));

    }


}
