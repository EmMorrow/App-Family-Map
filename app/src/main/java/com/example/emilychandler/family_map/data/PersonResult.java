package com.example.emilychandler.family_map.data;

import java.util.ArrayList;

public class PersonResult {
    private ArrayList<Person> data;
    private String message;

    public PersonResult() {
        data = null;
        message = null;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
