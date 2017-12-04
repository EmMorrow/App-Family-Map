package com.example.emilychandler.family_map.client;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.RegisterResult;
import com.example.emilychandler.family_map.data.User;
import com.example.emilychandler.family_map.ui.MapFragment;
import com.example.emilychandler.family_map.ui.RegisterFragment;

/**
 * Created by emilychandler on 11/13/17.
 */

public class RegisterTask extends AsyncTask<User, Integer, RegisterResult> {
    Context context;
    String serverHost, serverPort;
    private onPostExecuteListener listener = null;

    public interface onPostExecuteListener {
        void onPostExecute(String output);
    }

    public void setOnPostExecuteListener(onPostExecuteListener listener) {
        this.listener = listener;
    }
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
            if(listener != null) {
                listener.onPostExecute("unsuccessful");
            }
        }
        else if (result.getAuthToken() != null) {
            Model m = Model.getInstance();
            m.setAuthToken(result.getAuthToken());
            Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show();

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