package org.example.contact_book.service;


import org.example.contact_book.model.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ContactManagerIntegrationTest {

    private static final String TEST_FILE_PATH = "src/test/resources/test_contact_book.bin";
    private ContactManager contactManager;

    @BeforeEach
    void setUp() {
        contactManager = new ContactManager(TEST_FILE_PATH);
        createTestResourceDirectory();
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @DisplayName("ContactManager - add and load contact from file")
    void addAndLoadContactManagerFromFile() {
        // arrange
        Contact testContact = new Contact("Іван", "Мельник", "+380501234567",
                "ivan@email.com", "Друг по роботі");

        contactManager.putContactToBase(testContact);

        // act

        ContactManager newContactManager = new ContactManager(TEST_FILE_PATH);
        Map<String, Contact> loadedContacts = contactManager.getContactStorage();
        Contact contact = loadedContacts.values().iterator().next();

        assertEquals(1, loadedContacts.size());
        assertEquals("Іван", contact.getName());
    }

    // UTILS

    private void createTestResourceDirectory() {

        File testResourceDirectory = new File("src/test/resources");
        if (!testResourceDirectory.exists()) {
            testResourceDirectory.mkdir();
        }
    }

}
