package org.example.contact_book2.dao;

import org.example.contact_book2.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDao {
    private final static Logger logger = LoggerFactory.getLogger(ContactDao.class);
    private final String url;

    public ContactDao(String url) {
        this.url = url;
        initDatabase();
    }

    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS contacts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    name TEXT NOT NULL,
                    surname TEXT,
                    phone TEXT NOT NULL,
                    email TEXT,
                    description TEXT
                    );
                    """;

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println("Помилка в базі даних");
            logger.error("Помилка ініціалізації БД {}", e.getMessage());
        }
    }

    public void addContact(Contact contact) {
        String sql = "INSERT INTO contacts(name, surname, phone, email, description) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getSurname());
            pstmt.setString(3, contact.getPhoneNumber());
            pstmt.setString(4, contact.getEmail());
            pstmt.setString(5, contact.getDescription());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Помилка при додаванні контакту");
            logger.error("Помилка при додаванні контакту {}", e.getMessage());
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT name, surname, phone, email, description FROM contacts";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("description")
                );
                contacts.add(contact);
            }

        } catch (SQLException e) {
            System.out.println("Помилка при завантаженні контактів");
            logger.error("Помилка при отриманні контактів {}", e);
        }
        return contacts;
    }

    // Пошук по імені або частині імені
    public List<Contact> searchByName(String name) {
        String sql = "SELECT name, surname, phone, email, description FROM contacts " +
                "WHERE name LIKE ?";

        return searchContacts(sql, name);
    }

    // Пошук по прізвищі або частині прізвища
    public List<Contact> searchBySurname(String surname) {
        String sql = "SELECT name, surname, phone, email, description FROM contacts " +
                "WHERE surname LIKE ?";

        return searchContacts(sql, surname);
    }

    // Пошук по email або його частині
    public List<Contact> searchByEmail(String email) {
        String sql = "SELECT name, surname, phone, email, description FROM contacts " +
                "WHERE email LIKE ?";

        return searchContacts(sql, email);
    }

    // Пошук по номеру телефону або його частині
    public List<Contact> searchByPhoneNumber(String phone) {
        String sql = "SELECT name, surname, phone, email, description FROM contacts " +
                "WHERE phone LIKE ?";

        return searchContacts(sql, phone);
    }

    private List<Contact> searchContacts(String sql, String parameter) {
        List<Contact> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + parameter + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("description")
                );
                result.add(contact);
            }
        } catch (SQLException e) {
            System.out.println("Помилка пошуку");
            logger.error("Помилка при пошуку контактів {}", e);
        }
        return result;
    }
}
