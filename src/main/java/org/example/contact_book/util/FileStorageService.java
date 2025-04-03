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

    private boolean isFileExistAndNotEmpty() {
        File file = new File(pathToContactBase);
        if (!file.exists()) return false;
        if (file.length() == 0) {
            file.delete();
            System.out.println("Файл існує але він порожній");
            return false;
        }
        return true;
    }

    public Map<String, Contact> readDataFromFile() {
        if (!isFileExistAndNotEmpty()) {
            return new HashMap<>();
        } else {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(pathToContactBase)))) {

                  Object object = objectInputStream.readObject();
                  if (object instanceof Map) {
                      return (Map<String, Contact>) object;

                  } else {
                      throw new RuntimeException("Файл не містить валідну структуру даних:" +
                              " очікується Map<String, Contact>.");
            }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Файл не знайдено" + pathToContactBase, e);
            } catch (IOException e) {
                throw new RuntimeException("Помилка читання файлу", e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Клас не знайдено" ,e);
            }
        }
    }

    public void writeDataToFile(Map<String, Contact> contactMap) {
        if (contactMap == null || pathToContactBase == null) {
        throw new IllegalArgumentException("Мапа контактів або шлях до файлу не можуть бути null.");
    }
        if (!isFileExistAndNotEmpty()) {
            try {
                Files.createFile(Path.of(pathToContactBase));
            } catch (IOException e) {
                throw new RuntimeException("Файл " + "не існує, помилка при створенні нового файлу" ,e);
            }
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(pathToContactBase)))) {
            objectOutputStream.writeObject(contactMap);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Помилка запису у файл " + pathToContactBase,e);
        } catch (IOException e) {
            throw new RuntimeException("Помилка виводу ",e);
        }
    }
}
