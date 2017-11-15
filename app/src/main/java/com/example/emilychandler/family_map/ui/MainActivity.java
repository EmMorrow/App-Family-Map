package com.example.emilychandler.family_map.ui;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Model;

public class MainActivity extends AppCompatActivity {
    LoginFragment loginFragment;
    MapFragment mapFragment;
    //RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (Model.getInstance().getAuthToken() == null) {
            loginFragment = new LoginFragment();
            transaction.replace(R.id.contentFragment, loginFragment);
        }
        else {
            mapFragment = new MapFragment();
            transaction.replace(R.id.contentFragment, mapFragment);
        }
        transaction.commit();
    }
}
