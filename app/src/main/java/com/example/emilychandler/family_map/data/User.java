package com.example.emilychandler.family_map.data;

import java.util.UUID;

/**
 * Stores User information
 */
public class User {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public User() {
        userName = null;
        password = null;
        email = null;
        firstName = null;
        lastName = null;
        gender = null;
        personID = generatePersonId();
    }

    public User(String userName, String password, String email, String firstName, String lastName, String gender, String personId) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personId;
    }

    public String generatePersonId() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        return id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
    }
}
