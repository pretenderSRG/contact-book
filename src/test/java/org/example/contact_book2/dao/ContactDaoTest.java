package org.example.contact_book2.dao;

import ch.qos.logback.classic.LoggerContext;
import org.example.contact_book2.model.Contact;
import org.example.contact_book2.service.ContactService;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactDaoTest {
    private final static Logger logger = LoggerFactory.getLogger(ContactDaoTest.class);
    private ContactDao contactDao;
    private static final String testDbUrl = "jdbc:sqlite:test-db.sqlite";
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        System.setProperty("logback.configurationFile", "src/test/resources/logback_test.xml");
        contactDao = new ContactDao(testDbUrl);
        contactService = new ContactService(contactDao);

        putContactsToBase();
    }


    @Test
    void addContact() {
        // arrange
        Contact contact = new Contact("Eddy", "Brock",
                "0997876655", "eddybrock@mail.ua", "Venom");
        // act
        contactDao.addContact(contact);
        List<Contact> result = contactDao.getAllContacts();

        // assert
        assertTrue(result.contains(contact));
    }

    @Test
    void getAllContacts() {
        // arrange
        Contact contact = new Contact("John", "Smith", "0991234567",
                "jsmith@mail.com","contact 1");

        // act
        List<Contact> result = contactService.getAllContacts();

        // assert
        assertEquals(2, result.size());
        assertTrue(result.contains(contact));


    }

    @Test
    void searchByName_WhenFoundContact() {
        // arrange
        String name = "John";

         // act
        List<Contact> result = contactDao.searchByName(name);

        // assert
        assertEquals(name, result.get(0).getName());
    }

    @Test
    void searchByName_WhenNotFoundContact() {
        // arrange
        String name = "Jax";

         // act
        List<Contact> result = contactDao.searchByName(name);

        // assert
        assertTrue(result.isEmpty());
    }

    @Test
    void searchBySurname_WhenFoundContact() {
        // arrange
        String surname = "Smith";

        // act
        List<Contact> result = contactDao.searchBySurname(surname);

        // assert
        assertEquals(surname, result.get(0).getSurname());
        assertEquals(1, result.size());
    }

    @Test
    void searchBySurname_WhenNotFoundContact() {
        // arrange
        String surname = "Sam";

        // act
        List<Contact> result = contactDao.searchBySurname(surname);

        // assert
       assertTrue(result.isEmpty());
    }

    @Test
    void searchByEmail_WhenFoundContact() {
        // arrange
        String email = "jdoe@mail.com";

        // act
        List<Contact> result = contactDao.searchByEmail(email);

        // assert
        assertEquals(email, result.get(0).getEmail());

    }

       @Test
    void searchByEmail_WhenNotFoundContact() {
        // arrange
        String email = "werte@mail.com";

        // act
        List<Contact> result = contactDao.searchByEmail(email);

        // assert
        assertTrue(result.isEmpty());

    }

    @Test
    void searchByPhoneNumber_WhenFoundContact() {
                // arrange
        String phoneNumber = "0991234567";

        // act
        List<Contact> result = contactDao.searchByPhoneNumber(phoneNumber);

        // assert
        assertEquals(1, result.size());
        assertEquals(phoneNumber, result.get(0).getPhoneNumber());
    }

     @Test
    void searchByPhoneNumber_WhenNotFoundContact() {
                // arrange
        String phoneNumber = "0991234111";

        // act
        List<Contact> result = contactDao.searchByPhoneNumber(phoneNumber);

        // assert
        assertTrue(result.isEmpty());
    }

    @Test
    void updateContact_WhenUpdateNewName() {
        // arrange
        List<Contact> contacts = contactDao.getAllContacts();
        Contact contact = contacts.get(0);
        String newName = "Jax";
        contact.setName(newName);

        // act
        contactDao.updateContact(contact);
        List<Contact> updatedList = contactDao.searchByName(newName);

        // assert
        assertEquals(newName, updatedList.get(0).getName());
    }

    @Test
    void deleteContactByPhone() {
        // arrange
        List<Contact> contacts = contactDao.getAllContacts();
        Contact contact = contacts.get(0);

        // act
        contactDao.deleteContactByPhone(contact.getPhoneNumber());
        List<Contact> result = contactDao.searchByPhoneNumber(contact.getPhoneNumber());

        // assert
        assertTrue(result.isEmpty());
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = DriverManager.getConnection(testDbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS contacts");
        } catch (SQLException e) {
            logger.error("Не вдалося очистити БД після тесту: {}", e.getMessage());
        }

        try {
            Files.deleteIfExists(Path.of("test-db.sqlite"));
        } catch (IOException e) {
            logger.error("Не вдалось видалити БД");
        }
    }


    // UTILS
    private void createDataBase() {
        logger.info("Create DB");
        String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
                "                id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "                name TEXT NOT NULL, " +
                "                surname TEXT," +
                "                phone TEXT NOT NULL," +
                "                email TEXT," +
                "                description TEXT" +
                "                );";

        try (Connection conn = DriverManager.getConnection(testDbUrl);
             Statement stmt = conn.createStatement()) {
//            stmt.execute("DROP TABLE IF EXISTS contacts");
            stmt.execute(sql);
        } catch (SQLException e) {
            logger.error("Помилка в тесті {}", e.getMessage());

        }
    }

    private void putContactsToBase() {

        if (!tableExists("contacts")) {
            createDataBase();
        }
        String sql = "INSERT INTO contacts(name, surname, phone, email, description) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(testDbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // add contact 1
            pstmt.setString(1, "John");
            pstmt.setString(2, "Smith");
            pstmt.setString(3, "0991234567");
            pstmt.setString(4, "jsmith@mail.com");
            pstmt.setString(5, "contact 1");
            pstmt.executeUpdate();

            // add contact 2
            pstmt.setString(1, "Jane");
            pstmt.setString(2, "Doe");
            pstmt.setString(3, "0501234567");
            pstmt.setString(4, "jdoe@mail.com");
            pstmt.setString(5, "contact 2");
            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Помилка при наповненні бази {}", e.getMessage());
        }

    }

    private boolean tableExists(String tableName) {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
        try (Connection conn = DriverManager.getConnection(testDbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tableName);
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            logger.error("Помилка при перевірці наявності таблиці", e);
            return false;
        }
    }
}