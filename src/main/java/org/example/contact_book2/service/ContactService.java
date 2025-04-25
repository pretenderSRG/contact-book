package org.example.contact_book2.service;

import org.example.contact_book2.dao.ContactDao;
import org.example.contact_book2.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ContactService {
    private final static Logger logger = LoggerFactory.getLogger(ContactDao.class);
    private final ContactDao contactDao;
    private final static String emailRegex = "\\w+@\\w+\\.[a-zA-Z]{2,6}";
    private final static String phoneRegex = "/d{10}|/d{12}";

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void addContact(Contact contact) {
        String normalizePhoneNumber = normalizePhoneNumber(contact.getPhoneNumber());

        if (isPhoneNumberValid(normalizePhoneNumber)) {
            contact.setPhoneNumber(normalizePhoneNumber);
            String contactEmail = contact.getEmail();
            if (isEmailValid(contactEmail)) {
                contactDao.addContact(contact);
            } else {
                logger.warn("Не правильний mail {}", contactEmail);
            }
        } else {
            logger.warn("Не правильний номер телефону {}", contact.getPhoneNumber());
        }
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public List<Contact> searchByName(String name) {
        return contactDao.searchByName(name);
    }

    public List<Contact> searchBySurname(String surname) {
        return contactDao.searchBySurname(surname);
    }

    public List<Contact> searchByPhone(String phone) {
        return contactDao.searchByPhoneNumber(phone);
    }

    public List<Contact> searchByEmail(String email) {
        return contactDao.searchByName(email);
    }

    public void deleteContactByPhone(String phone) {
        contactDao.deleteContactByPhone(phone);
    }

    public void updateContact(Contact contact) {
        contactDao.updateContact(contact);
    }

    private String normalizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[^/d]", "");
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) return false;
        return phoneNumber.matches(phoneRegex);
    }

    public static boolean isEmailValid(String contactEmail) {
        if (contactEmail == null) return false;
        return contactEmail.matches(emailRegex) || contactEmail.isEmpty();
    }

}
