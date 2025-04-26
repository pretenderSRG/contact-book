package org.example.contact_book2;

import org.example.contact_book2.dao.ContactDao;
import org.example.contact_book2.service.ContactService;
import org.example.contact_book2.ui.ConsoleUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        System.out.println("Контакт Бук 2.0 — SQLite edition \uD83D\uDCD2");

        String dbUrl = "jdbc:sqlite:src/main/resources/contact_book.db";
        ContactDao contactDao = new ContactDao(dbUrl);
        ContactService contactService = new ContactService(contactDao);

        ConsoleUI ui = new ConsoleUI(contactService);
        ui.start();
    }


}