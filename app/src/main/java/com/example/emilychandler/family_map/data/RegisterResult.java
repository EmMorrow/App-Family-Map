package com.example.emilychandler.family_map.data;

/**
 * Stores the result of the Register service
 */

public class RegisterResult {
    private String authToken;
    private String userName;
    private String personId;
    private String message;

    public RegisterResult() {
        authToken = null;
        userName = null;
        personId = null;
        message = null;
    }

    public RegisterResult(String authToken, String userName, String personId) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personId;
    }

    public void setPersonID(String personId) {
        this.personId = personId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
