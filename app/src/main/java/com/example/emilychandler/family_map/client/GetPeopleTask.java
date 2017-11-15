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
        m.setMypeople(result.getData());

        String name = getCurrPerson(result.getData());
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
    }

    private String getCurrPerson(ArrayList<Person> myPeople) {
        Person myPerson = myPeople.get(myPeople.size() - 1);
        String name = myPerson.getFirstName() + " " + myPerson.getLastName();
        return name;
    }
}