package org.example.contact_book2.service;

import org.example.contact_book2.dao.ContactDao;
import org.example.contact_book2.model.Contact;
import org.example.contact_book2.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ContactService {
    private final static Logger logger = LoggerFactory.getLogger(ContactDao.class);
    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void addContact(Contact contact) {
        String normalizePhoneNumber = ValidationUtils.normalizePhoneNumber(contact.getPhoneNumber());

        if (ValidationUtils.isPhoneNumberValid(normalizePhoneNumber)) {
            contact.setPhoneNumber(normalizePhoneNumber);
            String contactEmail = contact.getEmail();
            if (ValidationUtils.isEmailValid(contactEmail)) {
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
        return contactDao.searchByEmail(email);
    }

    public void deleteContactByPhone(String phone) {
        contactDao.deleteContactByPhone(phone);
    }

    public void updateContact(Contact contact) {
        contactDao.updateContact(contact);
    }

}
