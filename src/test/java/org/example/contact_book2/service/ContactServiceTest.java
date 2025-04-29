package org.example.contact_book2.service;

import org.example.contact_book2.dao.ContactDao;
import org.example.contact_book2.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactServiceTest {
    private ContactDao mockContactDao;
    private ContactService contactService;
    private Contact sampleContact;

    @BeforeEach
    public void setUp() {
        mockContactDao = mock(ContactDao.class);
        contactService = new ContactService(mockContactDao);
        sampleContact = new Contact("Jane", "Smith", "0501234567", "jane@mail.com", "friend");

    }

    @ParameterizedTest
    @CsvSource({"099 123 45 67, example@mail.com", "38099123 55 44, qwe@io.ua", "099 12 3 4 5 6 7, ex-mpl@jkl.com.ua"})
    void addContact_WhenGivenContactWithCorrectNumberAndCorrectEmail_ShouldSaveContactToBase(
            String number, String email) {
        // arrange
        Contact contact = new Contact("John", "Doe", number, email, "friend");

        // act
        contactService.addContact(contact);

        // assert
        verify(mockContactDao, times(1)).addContact(contact);
    }

    @ParameterizedTest
    @CsvSource({"2342340501234567, example@com.ua", "213123, expl-op@mail.oi", "08745633341, first@yahoo.com"})
    void addContact_WhenGivenContactWithIncorrectPhoneNumber_ShouldNotSaveContactToBase(
            String number, String email) {
        // arrange
        Contact contact = new Contact("John", "Doe", number, email, "friend");

        // act
        contactService.addContact(contact);

        // assert
        verify(mockContactDao, never()).addContact(any(Contact.class));
    }

    @ParameterizedTest
    @CsvSource({"2342340501234567, example@com.ua", "099 123 34 55, @mail.oi", "08745633341, @yahoo.com"})
    void addContact_WhenGivenContactWithIncorrectPhoneNumberAndIncorrectEmail_ShouldNotSaveContactToBase(
            String number, String email) {
        // arrange
        Contact contact = new Contact("John", "Doe", number, email, "friend");

        // act
        contactService.addContact(contact);

        // assert
        verify(mockContactDao, never()).addContact(any(Contact.class));
    }

    @Test
    void getAllContacts_WhenContactInBase_ShouldReturnAllContacts() {
        // arrange
        List<Contact> listOfContacts = List.of(new Contact("John", "Dou", "0991234567",
                        "jhon@mail.com", "contact 1"),
                new Contact("Jane", "Smith", "0501234567",
                        "jane@mail.com", "contact 2"),
                new Contact("Eddy", "Coule", "0661234567",
                        "ed@mail.com", "contact 3")
        );
        when(mockContactDao.getAllContacts()).thenReturn(listOfContacts);

        // act
        List<Contact> allContacts = contactService.getAllContacts();

        // assert
        assertEquals(3, allContacts.size());
        assertEquals("John", allContacts.get(0).getName());
    }

    @Test
    void getAllContacts_WhenContactNotInBase_ShouldReturnEmptyList() {
        // arrange
        when(mockContactDao.getAllContacts()).thenReturn(List.of());

        // assert
        assertTrue(contactService.getAllContacts().isEmpty());
        assertNotNull(contactService.getAllContacts());
    }

    @ParameterizedTest
    @CsvSource({
            "Jane, Dou, 0501234567, jane@mail.com, contact 1",
            "John, Smith, 0991234567, john@mail.com, contact 2",
            "Eddy, Link, 0661234567, ed@mail.com, contact 3"
    })
    void searchByName_WhenGivenCorrectName_ShouldReturnContact(
            String name, String surname,
            String phone, String email, String description) {
        // arrange
        Contact contact = new Contact(name, surname, phone, email, description);
        when(mockContactDao.searchByName(name)).thenReturn(List.of(contact));

        // act
        List<Contact> result = contactService.searchByName("Jane");

        // assert
        assertEquals(1, result.size());
        Contact found = result.get(0);
        assertEquals(name, found.getName());
        assertEquals(surname, found.getSurname());
        assertEquals(email, found.getEmail());
    }

    @Test
    void searchBySurname_WhenGivenCorrectSurname_ShouldReturnContact() {
        // arrange
        String searchingSurname = "Smith";
        when(mockContactDao.searchBySurname(searchingSurname)).thenReturn(List.of(sampleContact));

        // act
        List<Contact> result = contactService.searchBySurname(searchingSurname);

        // assert
        assertEquals(1, result.size());
        Contact found = result.get(0);
        assertEquals(searchingSurname, found.getSurname());
    }

    @Test
    void searchBySurname_WhenNotFoundContact_ShouldReturnEmptyList() {
        // arrange
        String searchingSurname = "Smith";
        when(mockContactDao.searchBySurname(searchingSurname)).thenReturn(List.of());

        // act
        List<Contact> result = contactService.searchBySurname(searchingSurname);

        // assert
        assertTrue(result.isEmpty());
    }

    @Test
    void searchByPhone_WhenGivenCorrectPhone_ShouldReturnContact() {
        // arrange
        String searchingPhone = "0501234567";
        when(mockContactDao.searchByPhoneNumber(searchingPhone)).thenReturn(List.of(sampleContact));

        // act
        List<Contact> result = contactService.searchByPhone(searchingPhone);

        // assert
        assertEquals(1, result.size());
        assertTrue(result.contains(sampleContact));
    }

    @Test
    void searchByPhone_WhenContactNotFound_ShouldReturnEmptyList() {
        // arrange
        String searchingPhone = "0501234567";
        when(mockContactDao.searchByPhoneNumber(searchingPhone)).thenReturn(List.of());

        // act
        List<Contact> result = contactService.searchByPhone(searchingPhone);

        // assert
        assertTrue(result.isEmpty());
        assertNotNull(result);
    }

    @Test
    void searchByEmail_WhenGivenCorrectEmail_ShouldReturnContact() {
        // arrange
        String searchingEmail = "jane@mail.com";
        when(mockContactDao.searchByEmail(searchingEmail)).thenReturn(List.of(sampleContact));

        // act
        List<Contact> result = contactService.searchByEmail(searchingEmail);

        // assert
        assertEquals(1, result.size());
        Contact found = result.get(0);
        assertEquals(searchingEmail, found.getEmail());
    }

    @Test
    void searchByEmail_WhenContactNotFound_ShouldReturnEmptyList() {
        // arrange
        String searchingEmail = "jane@mail.com";
        when(mockContactDao.searchByEmail(searchingEmail)).thenReturn(List.of());

        // act
        List<Contact> result = contactService.searchByEmail(searchingEmail);

        // assert
        assertTrue(result.isEmpty());
        assertNotNull(result);
    }

    @Test
    void deleteContactByPhone_WhenContactInBase_ShouldDeleteContact() {
        // arrange
        String phoneNumber = "0991234567";
        // act
        contactService.deleteContactByPhone(phoneNumber);
        // assert
        verify(mockContactDao, times(1)).deleteContactByPhone(phoneNumber);
    }

    @Test
    void updateContact_WhenContactInBase_ShouldUpdateContact() {
        // act
        contactService.updateContact(sampleContact);
        // assert
        verify(mockContactDao, times(1)).updateContact(sampleContact);
    }
}