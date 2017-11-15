package com.example.emilychandler.family_map.ui;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.client.LoginTask;
import com.example.emilychandler.family_map.client.RegisterTask;
import com.example.emilychandler.family_map.data.LoginRequest;
import com.example.emilychandler.family_map.data.User;


public class RegisterFragment extends Fragment implements View.OnClickListener{
    private Button register,signin;
    private EditText serverHost, serverPort, username, password, firstName, lastName, email;
    private RadioGroup gender;
    private String genderS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment,container,false);
        username = (EditText)v.findViewById(R.id.username);
        password = (EditText)v.findViewById(R.id.password);
        serverPort = (EditText)v.findViewById(R.id.server_port);
        serverHost = (EditText)v.findViewById(R.id.server_host);
        firstName = (EditText)v.findViewById(R.id.first_name);
        lastName = (EditText)v.findViewById(R.id.last_name);
        email = (EditText)v.findViewById(R.id.email);

        signin = (Button)v.findViewById(R.id.sign_in);
        register = (Button)v.findViewById(R.id.register);

        gender = (RadioGroup)v.findViewById(R.id.gender);

        signin.setOnClickListener(this);
        register.setOnClickListener(this);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.female) {
                    genderS = "F";
                }
                else if (checkedId == R.id.male) {
                    genderS = "M";
                }
            }
        });
        return v;
    }

    public void onRegister() {
        RegisterTask register = new RegisterTask(getActivity(), serverHost.getText().toString(), serverPort.getText().toString());
        User request = new User();
        request.setLastName(lastName.getText().toString());
        request.setFirstName(firstName.getText().toString());
        request.setEmail(email.getText().toString());
        request.setUsername(username.getText().toString());
        request.setPassword(password.getText().toString());
        request.setGender(genderS);
        register.execute(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                LoginFragment l = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentFragment, l);
                transaction.commit();
                break;
            case R.id.register:
                onRegister();
                break;
        }
    }
}
