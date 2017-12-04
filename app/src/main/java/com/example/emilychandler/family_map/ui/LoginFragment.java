package com.example.emilychandler.family_map.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.client.LoginTask;
import com.example.emilychandler.family_map.data.LoginRequest;

public class LoginFragment extends Fragment implements View.OnClickListener, LoginTask.onPostExecuteListener {
    private Button signin, register;
    private EditText username, password, serverPort, serverHost;
    private TextView link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);
        username = (EditText)v.findViewById(R.id.username);
        password = (EditText)v.findViewById(R.id.password);
        serverPort = (EditText)v.findViewById(R.id.server_port);
        serverHost = (EditText)v.findViewById(R.id.server_host);

        username.setText("emily");
        password.setText("emily");
        serverPort.setText("5050");
//        serverHost.setText("10.37.66.23"); // school
        serverHost.setText("192.168.1.154"); // home

        signin = (Button)v.findViewById(R.id.sign_in);
        register = (Button)v.findViewById(R.id.register);
        signin.setOnClickListener(this);
        register.setOnClickListener(this);
        return v;
    }

    public void onLogin() {
        // new LoginTask (async task).execute(Params)
        System.out.println("hey");
        System.out.println(serverHost.getText().toString());
        LoginTask login = new LoginTask(getActivity(), serverHost.getText().toString(), serverPort.getText().toString());
        login.setOnPostExecuteListener(this);
        LoginRequest request = new LoginRequest(username.getText().toString(), password.getText().toString());
        login.execute(request);

//        GetPeopleTask getPeople = new GetPeopleTask(getActivity(), serverHost.getText().toString(), serverPort.getText().toString());
//        getPeople.execute(Model.getInstance().getAuthToken());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                onLogin();
                break;
            case R.id.register:
                RegisterFragment r = new RegisterFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentFragment, r);
                transaction.commit();
                break;
        }
    }

    @Override
    public void onPostExecute(String result) {
        if (result.equals("successful")) {
            MapFragment r = new MapFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, r);
            transaction.commit();
        }
    }


}
