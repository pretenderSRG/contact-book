package org.contacts.backend.controller;

import org.contacts.backend.entity.Contact;
import org.contacts.backend.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {
    private final ContactService service;
    private final ContactService contactService;

    public ContactController(ContactService service, ContactService contactService) {
        this.service = service;
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> getAll() {
        return service.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getById(@PathVariable Long id) {
        return service.getContactByIs(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public Contact create(@RequestBody Contact contact) {
        return service.createContact(contact);
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id, @RequestBody Contact contact) {
        return service.updateContact(id, contact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteContact(id);
        return ResponseEntity.noContent().build();
    }


}
