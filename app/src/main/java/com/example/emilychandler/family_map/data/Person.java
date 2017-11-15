package com.example.emilychandler.family_map.data;

import java.util.UUID;

/**
 * Stores Person information
 */
public class Person {
    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;
    private String message;

    public Person() {
        personID = null;
        descendant = null;
        firstName = null;
        lastName = null;
        gender = null;
        father = null;
        mother = null;
        spouse = null;
        message = null;
    }
    public Person(String descendant, String firstName, String lastName, String gender, String father, String mother, String spouse) {
        generatePersonId();
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }


    public void generatePersonId() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        personID = id;
    }

    public void setPersonIdNull() {
        personID = null;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
