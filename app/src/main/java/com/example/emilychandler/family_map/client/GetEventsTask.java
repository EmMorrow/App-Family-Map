package com.example.emilychandler.family_map.client;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.EventResult;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.PersonResult;
import com.example.emilychandler.family_map.ui.LoginFragment;
import com.example.emilychandler.family_map.ui.SettingsActivity;

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

    private LoginFragment lFrag;
    private SettingsActivity sActivity;

    public GetEventsTask(Context context, String serverHost, String serverPort) {
        this.context = context;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    protected EventResult doInBackground(String... auth) {
        System.out.println("get events do in backgroud");
        ServerProxy server = new ServerProxy(serverHost,serverPort);
        return server.getEvents(auth[0]);
    }

    @Override
    protected void onPostExecute(EventResult result) {
        System.out.println("get events on post execute");
        Model m = Model.getInstance();
        m.setEvents(fillEvents(result.getData()));
        if (lFrag != null) lFrag.setEventsTask(true);
        else sActivity.setEventsTask(true);

    }

    public void setFragment(LoginFragment lFrag) {
        this.lFrag = lFrag;
    }

    public void setsActivity(SettingsActivity sActivity) {
        this.sActivity = sActivity;
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
