package com.example.emilychandler.family_map.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.Model;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by emilychandler on 11/22/17.
 */

public class MapActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.person_menu, menu);
        menu.findItem(R.id.goToTop).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_angle_double_up)
                        .actionBarSize()
                        .color(Color.WHITE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        startActivity(new Intent(MapActivity.this, MainActivity.class));
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null) {
            Event myEvent =(Event) b.get("MyEvent");
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(b);
        transaction.replace(R.id.contentFragment, mapFragment);

        transaction.commit();

    }
}
