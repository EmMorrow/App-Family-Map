package com.example.emilychandler.family_map.client;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.EventResult;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.PersonResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by emilychandler on 11/27/17.
 */

public class GetEventsTask extends AsyncTask<String, Integer, EventResult> {
    private Context context;
    private String serverHost, serverPort;

    public GetEventsTask(Context context, String serverHost, String serverPort) {
        this.context = context;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    protected EventResult doInBackground(String... auth) {
        ServerProxy server = new ServerProxy(serverHost,serverPort);
        return server.getEvents(auth[0]);
    }

    protected void onPostExecute(EventResult result) {
        Model m = Model.getInstance();
        m.setEvents(fillEvents(result.getData()));
        m.setMyevents(result.getData());
    }

    private Map<String,Event> fillEvents(List<Event> events) {
        Map<String, Event> eventMap = new HashMap<>();
        for(int i = 0; i < events.size(); i++) {
            Event curr = events.get(i);
            eventMap.put(curr.getEventId(),curr); // used to be getPerson
        }
        return eventMap;
    }
}
