package com.example.emilychandler.family_map.ui;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Model;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    LoginFragment loginFragment;
    MapFragment mapFragment;
    //RegisterFragment registerFragment;
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.fragment_map, menu);
//
//        menu.findItem(R.id.filter).setIcon(
//                new IconDrawable(this, FontAwesomeIcons.fa_filter)
//                        .actionBarSize()
//                        .color(Color.WHITE));
//        menu.findItem(R.id.search).setIcon(
//                new IconDrawable(this, FontAwesomeIcons.fa_search)
//                        .actionBarSize()
//                        .color(Color.WHITE));
//        menu.findItem(R.id.settings).setIcon(
//                new IconDrawable(this, FontAwesomeIcons.fa_gear)
//                        .actionBarSize()
//                        .color(Color.WHITE));
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.filter :
//                startActivity(new Intent(this, FilterActivity.class));
//                break;
//            case R.id.search :
//                startActivity(new Intent(this, SearchActivity.class));
//                break;
//            case R.id.settings :
//                startActivity(new Intent(this, SettingsActivity.class));
//                break;
//            default :
//                return super.onOptionsItemSelected(menuItem);
//        }
//        return true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());

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

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
}
