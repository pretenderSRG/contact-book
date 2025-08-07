package org.example.contact_book.service;

import org.example.contact_book.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContactSearchTest {
    private ContactManager mockContactManager;
    private Map<String, Contact> mockContactStorage;
    private ContactSearch contactSearch;
    private ContactEditor mockEditor;

    @BeforeEach
    void setUp() {
        mockContactManager = mock(ContactManager.class);
        mockEditor = mock(ContactEditor.class);
        mockContactStorage = new HashMap<>();
        mockContactStorage.put("+380123456789", new Contact("Іван", "Мельник",
                "+380123456789", "ivan@example.com", "Друг"));
        mockContactStorage.put("+380987654321", new Contact("Олена", "Коваль",
                "+380987654321", "olena@example.com", "Колега"));

        when(mockContactManager.getContactStorage()).thenReturn(mockContactStorage);

        contactSearch = new ContactSearch(mockContactManager, mockEditor);

    }

    @Test
    void searchByPhoneNumber_ExactMatch() {
        String input = "+380123456789";
        List<Contact> searchResult = contactSearch.searchByPhoneNumber(input);

        assertEquals(1, searchResult.size());
        assertFalse(searchResult.isEmpty());
        assertEquals("+380123456789", searchResult.get(0).getPhoneNumber());
    }

    @Test
    void searchByPhoneNumber_PartialMatch() {
        String input = "1234";
        List<Contact> searchResult = contactSearch.searchByPhoneNumber(input);

        assertEquals(1, searchResult.size());
        assertFalse(searchResult.isEmpty());
        assertEquals("+380123456789", searchResult.get(0).getPhoneNumber());

    }

    @Test
    void searchByPhoneNumber_NoMatch() {
        String input = "12340";
        List<Contact> searchResult = contactSearch.searchByPhoneNumber(input);

        assertTrue(searchResult.isEmpty());

    }

    @Test
    void printSearchResult_FindOneContact() {
        List<Contact> searchResult = new ArrayList<>();
        searchResult.add(new Contact("Іван", "Мельник",
                "+380123456789", "ivan@example.com", "Друг"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStream));

        contactSearch.printSearchResult(searchResult);

        System.setOut(System.out);

        String output = outputStream.toString();

        assertTrue(output.contains("+380123456789"));
        assertTrue(output.contains("1)"));
        assertTrue(output.contains("Друг"));
    }

    @Test
    void printSearchResult_FindNoContact() {
        List<Contact> searchResult = new ArrayList<>();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStream));

        contactSearch.printSearchResult(searchResult);

        System.setOut(System.out);

        String output = outputStream.toString();

        assertTrue(output.contains("Нічого не знайдено"));


    }

    @Test
    void searchByName_ExactMatch() {
        String inputName = "Іван";
        List<Contact> searchResult = contactSearch.searchByName(inputName);

        assertEquals(1, searchResult.size());
        assertTrue(searchResult.get(0).getName().contains("Іван"));
    }

    @Test
    void searchByName_PartialMatch() {
        String inputName = "іва";
        List<Contact> searchResult = contactSearch.searchByName(inputName);

        assertEquals(1, searchResult.size());
        assertTrue(searchResult.get(0).getName().contains("Іван"));

    }

    @Test
    void searchByName_NoMatch() {
        String inputName = "Петро";
        List<Contact> searchResult = contactSearch.searchByName(inputName);

        assertTrue(searchResult.isEmpty());
    }

    @Test
    void searchBySurname_ExactMatch() {
        String inputSurname = "Мельник";
        List<Contact> searchResult = contactSearch.searchBySurname(inputSurname);

        assertEquals(1, searchResult.size());
        assertTrue(searchResult.get(0).getSurname().contains("Мельник"));
    }

    @Test
    void searchBySurname_PartialMatch() {
        String inputSurname = "мель";
        List<Contact> searchResult = contactSearch.searchBySurname(inputSurname);

        assertEquals(1, searchResult.size());
        assertTrue(searchResult.get(0).getSurname().contains("Мельник"));
    }

    @Test
    void searchBySurname_NoMatch() {
        String inputSurname = "Симоненко";
        List<Contact> searchResult = contactSearch.searchBySurname(inputSurname);

        assertTrue(searchResult.isEmpty());
    }

    @Test
    void search_UserChoice1() {

        String userInput = "1\n+380123456789\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        contactSearch.search(scanner);

        System.setOut(System.out);
        String output = outputStream.toString();

        assertTrue(output.contains("Іван"));
        verify(mockEditor, times(1)).editOrDelete(any());
    }

    @Test
    void search_UserChoice2() {

        String userInput = "2\nІван\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        contactSearch.search(scanner);

        System.setOut(System.out);
        String output = outputStream.toString();

        assertTrue(output.contains("Іван"));
        verify(mockEditor, times(1)).editOrDelete(any());
    }

    @Test
    void search_UserChoice3() {

        String userInput = "3\nМельник\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        contactSearch.search(scanner);

        System.setOut(System.out);
        String output = outputStream.toString();

        assertTrue(output.contains("Іван"));
        verify(mockEditor, times(1)).editOrDelete(any());
    }

    @Test
    void search_UserInputOtherNumber() {

        String userInput = "4\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        contactSearch.search(scanner);

        System.setOut(System.out);
        String output = outputStream.toString();

        assertTrue(output.contains("Введіть цифру від 0 до 3"));
        verify(mockEditor, never()).editOrDelete(any());
    }

    @Test
    void search_UserInputNotNumber() {

        String userInput = "x\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        contactSearch.search(scanner);

        System.setOut(System.out);
        String output = outputStream.toString();

        assertTrue(output.contains("Не правильний ввід. Введіть цифру 0 - 3"));
        verify(mockEditor, never()).editOrDelete(any());
    }


}
