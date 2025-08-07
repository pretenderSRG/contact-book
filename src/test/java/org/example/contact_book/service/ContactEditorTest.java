package org.example.contact_book.service;

import org.example.contact_book.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class ContactEditorTest {
    private ContactManager mockContactManager;
    private List<Contact> mockContactList;
    private ContactEditor contactEditor;

    @BeforeEach
    void setUp() {
        mockContactManager = mock(ContactManager.class);
        mockContactList = new ArrayList<>();

        mockContactList.add(new Contact("Іван", "Мельник",
                "+380123456789", "ivan@example.com", "Друг"));
        mockContactList.add(new Contact("Олена", "Коваль",
                "+380987654321", "olena@example.com", "Колега"));


        contactEditor = new ContactEditor(mockContactList, mockContactManager);
    }

    @Test
    void editOrDelete_IncorrectUserInput() {
        // arrange
        String incorrectInput = "3\n";

        ByteArrayOutputStream outputStream = redirectOutput();
        // act

        try {
            contactEditor.editOrDelete(getUserInput(incorrectInput));
        } finally {
            resetOutput();
        }
        // assert

        assertTrue(outputStream.toString().contains("Вибрано неіснуючий контакт. Спробуйте ще раз."));
        verify(mockContactManager, never()).deleteContact(any());
    }

    @Test
    void editOrDelete_UserChoiceDelete() {
        // arrange
        String input = "1\n2\n";
        Contact contact = mockContactList.get(0);

        //act
        contactEditor.editOrDelete(getUserInput(input));

        verify(mockContactManager, times(1)).deleteContact(contact);
    }

    @Test
    @DisplayName("editOrDelete - user chooses to edit name only")
    void editOrDelete_UserChoiceEditName() {
        // arrange
        String userInput = "1\n1\n" +
                "\nСвятослав\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n";
        Contact contact = mockContactList.get(0);

        // act
        contactEditor.editOrDelete(getUserInput(userInput));

        // assert
        verify(mockContactManager, times(1)).deleteContact(contact);

        ArgumentCaptor<Contact> captor = ArgumentCaptor.forClass(Contact.class);
        verify(mockContactManager, times(1)).putContactToBase(captor.capture());
        Contact newContact = captor.getValue();

        assertEquals("Святослав", newContact.getName());
        assertEquals("Мельник", newContact.getSurname());
        assertEquals("+380123456789", newContact.getPhoneNumber());

    }

    @Test
    void deleteContact_ShouldCallContactManagerDelete() {
        // arrange
        Contact contact = mockContactList.get(0);

        // act
        contactEditor.deleteContact(contact);

        // assert
        verify(mockContactManager, times(1)).deleteContact(contact);
    }

    // UTILS

    private Scanner getUserInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return new Scanner(inputStream);
    }

    private ByteArrayOutputStream redirectOutput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }

    private void resetOutput() {
        System.setOut(System.out);
    }
}