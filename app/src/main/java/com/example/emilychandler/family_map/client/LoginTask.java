package com.example.emilychandler.family_map.client;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.LoginRequest;
import com.example.emilychandler.family_map.data.LoginResult;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.ui.MapFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

/**
 * Created by emilychandler on 11/13/17.
 */

public class LoginTask extends AsyncTask<LoginRequest, Integer, LoginResult>{
    private Context context;
    private String serverHost, serverPort;
    private onPostExecuteListener listener = null;

    public interface onPostExecuteListener {
        void onPostExecute(String output);
    }

    public void setOnPostExecuteListener(onPostExecuteListener listener) {
        this.listener = listener;
    }

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
            if(listener != null) {
                listener.onPostExecute("unsuccessful");
            }
        }
        else if (result.getAuthToken() != null) {
            Model m = Model.getInstance();
            m.setAuthToken(result.getAuthToken());

            GetPeopleTask getPeople = new GetPeopleTask(context, serverHost, serverPort);
            GetEventsTask getEvents = new GetEventsTask(context, serverHost, serverPort);
            getPeople.execute(Model.getInstance().getAuthToken());
            getEvents.execute(Model.getInstance().getAuthToken());

            if(listener != null) {
                listener.onPostExecute("successful");
            }
        }
    }


}
