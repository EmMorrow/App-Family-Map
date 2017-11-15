package com.example.emilychandler.family_map.data;

import java.util.UUID;

/**
 * Stores Event information
 */
public class Event {
    private String eventID;
    private String descendant;
    private String personID;
    private String year;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private String message;

    public Event() {
        eventID = null;
        descendant = null;
        personID = null;
        year = null;
        latitude = null;
        longitude = null;
        country = null;
        city = null;
        eventType = null;
        message = null;
    }
    public Event(String descendant, String person, Double latitude, Double longitude, String country, String city, String eventType, String year) {
        generateEventId();
        this.descendant = descendant;
        this.personID = person;
        this.year = year;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.message = null;

    }

    public void generateEventId() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        eventID = id;
    }

    public String getEventId() {
        return eventID;
    }

    public void setEventId(String event_id) {
        this.eventID = event_id;
    }

    public void setEventIdNull(){
        eventID = null;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPerson() {
        return personID;
    }

    public void setPerson(String person) {
        this.personID = person;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
