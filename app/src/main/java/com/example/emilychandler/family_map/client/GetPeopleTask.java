package com.example.emilychandler.family_map.client;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.emilychandler.family_map.data.Model;

import com.example.emilychandler.family_map.data.LoginRequest;
import com.example.emilychandler.family_map.data.LoginResult;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.PersonResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by emilychandler on 11/14/17.
 */

public class GetPeopleTask extends AsyncTask<String, Integer, PersonResult> {
    private Context context;
    private String serverHost, serverPort;

    public GetPeopleTask(Context context, String serverHost, String serverPort) {
        this.context = context;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    protected PersonResult doInBackground(String... auth) {
        ServerProxy server = new ServerProxy(serverHost,serverPort);
        return server.getPeople(auth[0]);
    }

    protected void onPostExecute(PersonResult result) {
        Model m = Model.getInstance();
        Person currPerson = getCurrPerson(result.getData());
        Map<String,Person> peopleMap = fillPeople(result.getData());

        m.setPeople(peopleMap);
        m.setMypeople(result.getData());
        m.setCurrPerson(currPerson);
    }

    private Map<String,Person> fillPeople (List<Person> people) {
        Map<String, Person> personMap = new HashMap<String,Person>();
        for(int i = 0; i < people.size(); i++) {
            Person curr = people.get(i);
            personMap.put(curr.getPersonId(),curr);
        }
        return personMap;
    }

    private Person getCurrPerson(ArrayList<Person> myPeople) {
        Person myPerson = myPeople.get(myPeople.size() - 1);
        return myPerson;
    }
}