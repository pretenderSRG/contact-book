package org.example.contact_book.model;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    private String phoneNumber;
    private String name;
    private String surname;
    private String email;
    private String description;

    public Contact() {
    }

    public Contact(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public Contact(String name, String surname, String phoneNumber, String email, String description) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.description = description;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact contact)) return false;
        return Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, name, surname, email, description);
    }

    @Override
    public String toString() {
        return "\n" + name + "\n  " + surname + "\n\t" + phoneNumber + "\n\t" + email + "\n\t" + description;
    }

    @Override
    public int compareTo(Object o) {
        Contact other = (Contact) o;
        return name.compareTo(other.name);
    }
}
