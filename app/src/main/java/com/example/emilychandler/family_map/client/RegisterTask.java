package com.example.emilychandler.family_map.client;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.RegisterResult;
import com.example.emilychandler.family_map.data.User;

/**
 * Created by emilychandler on 11/13/17.
 */

public class RegisterTask extends AsyncTask<User, Integer, RegisterResult> {
    Context context;
    String serverHost, serverPort;

    public RegisterTask(Context context, String serverHost, String serverPort) {
        this.context = context;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    protected RegisterResult doInBackground(User... requests) {
        ServerProxy server = new ServerProxy(serverHost,serverPort);
        return server.register(requests[0]);
    }

    protected void onPostExecute(RegisterResult result) {
        if (result.getMessage() != null) {
            Toast.makeText(context, "Register Unsuccessful", Toast.LENGTH_SHORT).show();
        }
        else if (result.getAuthToken() != null) {
            Model m = Model.getInstance();
            m.setAuthToken(result.getAuthToken());
            Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show();
        }
    }
}