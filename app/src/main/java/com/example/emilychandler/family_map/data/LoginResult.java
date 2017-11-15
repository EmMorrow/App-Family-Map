package com.example.emilychandler.family_map.data;



/**
 * Stores the result of the Login service
 */

public class LoginResult {
    private String authToken;
    private String userName;
    private String personId;
    private String message;

    public LoginResult() {
        authToken = null;
        userName = null;
        personId = null;
        message = null;
    }

    public LoginResult(String authToken, String userName, String personId) {
        this.authToken = authToken;
        this.userName = userName;
        this.personId = personId;
        this.message = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
