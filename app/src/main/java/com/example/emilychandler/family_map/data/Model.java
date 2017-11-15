package com.example.emilychandler.family_map.data;

import java.util.ArrayList;

/**
 * Created by emilychandler on 11/11/17.
 */

public class Model {
    private static Model instance = null;
    private ArrayList<Person> mypeople;
    private ArrayList<Event> myevents;
    private String authToken;

    protected Model() {

    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public void setMypeople(ArrayList<Person> mypeople) {
        this.mypeople = mypeople;
    }

    public ArrayList<Person> getMypeople() {
        return mypeople;
    }

    public void setMyevents(ArrayList<Event> myevents) {
        this.myevents = myevents;
    }

    public ArrayList<Event> getMyevents() {
        return myevents;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
