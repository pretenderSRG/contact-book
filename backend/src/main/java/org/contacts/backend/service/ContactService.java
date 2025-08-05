package org.contacts.backend.service;

import org.contacts.backend.entity.Contact;
import org.contacts.backend.repositiry.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactByIs(Long id) {
        return contactRepository.findById(id);
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact updateContact(Long id,Contact contactDetails) {
        Contact contact = contactRepository.findById(id).orElseThrow();
        contact.setFirstName(contactDetails.getFirstName());
        contact.setLastName(contactDetails.getLastName());
        contact.setPhone(contactDetails.getPhone());
        contact.setEmail(contactDetails.getEmail());
        contact.setDescription(contactDetails.getDescription());
        return contactRepository.save(contact);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

}
