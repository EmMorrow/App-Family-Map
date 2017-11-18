package com.example.emilychandler.family_map.client;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.emilychandler.family_map.data.LoginRequest;
import com.example.emilychandler.family_map.data.LoginResult;
import com.example.emilychandler.family_map.data.Model;

import static java.security.AccessController.getContext;

/**
 * Created by emilychandler on 11/13/17.
 */

public class LoginTask extends AsyncTask<LoginRequest, Integer, LoginResult>{
    private Context context;
    private String serverHost, serverPort;

    public LoginTask(Context context, String serverHost, String serverPort) {
        this.context = context;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    protected LoginResult doInBackground(LoginRequest... requests) {
        ServerProxy server = new ServerProxy(serverHost,serverPort);
        return server.login(requests[0]);
    }

    protected void onPostExecute(LoginResult result) {
        if (result.getMessage() != null) {
            Toast.makeText(context, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
        else if (result.getAuthToken() != null) {
            Model m = Model.getInstance();
            m.setAuthToken(result.getAuthToken());
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();

            GetPeopleTask getPeople = new GetPeopleTask(context, serverHost, serverPort);
            getPeople.execute(Model.getInstance().getAuthToken());
        }
    }
}
