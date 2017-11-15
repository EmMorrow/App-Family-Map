package com.example.emilychandler.family_map.data;

/**
 * Stores information from the Login request
 */
public class LoginRequest {
    private String userName;
    private String password;

    public LoginRequest() {
        userName = null;
        password = null;
    }
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
