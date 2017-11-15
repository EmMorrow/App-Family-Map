package com.example.emilychandler.family_map.data;

import java.util.ArrayList;


/**
 * Created by emilychandler on 10/30/17.
 */

public class EventResult {
    private ArrayList<Event> data;
    private String message;

    public EventResult() {
        data = null;
        message = null;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
