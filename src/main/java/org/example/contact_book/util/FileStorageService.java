package org.example.contact_book.util;

import org.example.contact_book.model.Contact;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileStorageService {
    private String pathToContactBase;

    public FileStorageService(String pathToContactBase) {
        this.pathToContactBase = pathToContactBase;
    }

    public Map<String, Contact> readDataFromFile() {
        Path filePath = Path.of(pathToContactBase);

        if (Files.notExists(filePath)) {
            return new HashMap<>();
        }
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(pathToContactBase)))) {

            Object object = objectInputStream.readObject();
            if (object instanceof Map) {
                return (Map<String, Contact>) object;
            } else {
                throw new RuntimeException("Файл містить некоректні дані: очікується Map<String, Contact>.");
            }

        } catch (EOFException e) {
            // Якщо файл порожній – просто повертаємо нову мапу
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Помилка читання файлу " + pathToContactBase, e);
        }

    }

    public void writeDataToFile(Map<String, Contact> contactMap) {
        if (contactMap == null || pathToContactBase == null) {
            throw new IllegalArgumentException("Мапа контактів або шлях до файлу не можуть бути null.");
        }
        Path filePath = Path.of(pathToContactBase);
        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Помилка при створенні нового файлу " + pathToContactBase, e);
            }
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(pathToContactBase)))) {
            objectOutputStream.writeObject(contactMap);
        } catch (IOException e) {
            throw new RuntimeException("Помилка запису у файл " + pathToContactBase, e);
        }
    }
}
