package com.example.emilychandler.family_map.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.client.GetEventsTask;
import com.example.emilychandler.family_map.client.GetPeopleTask;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Settings;

/**
 * Created by emilychandler on 11/22/17.
 */

public class SettingsActivity extends AppCompatActivity {
    Switch lifeStoryLinesSwitch, spouseLinesSwitch, familyTreeLinesSwitch;
    Spinner lifeStoryLinesSpinner, spouseLinesSpinner, familyTreeLinesSpinner, mapTypeSpinner;
    Settings settings;
    RelativeLayout resync, logout;
    private boolean peopleTask, eventTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = Model.getInstance().getSettings();

        lifeStoryLinesSwitch = (Switch) findViewById(R.id.lifeStoryLinesSwitch);
        spouseLinesSwitch = (Switch) findViewById(R.id.spouseLinesSwitch);
        familyTreeLinesSwitch = (Switch) findViewById(R.id.familyTreeLinesSwitch);

        lifeStoryLinesSpinner = (Spinner) findViewById(R.id.lifeStoryLinesSpinner);
        spouseLinesSpinner = (Spinner) findViewById(R.id.spouseLinesSpinner);
        familyTreeLinesSpinner = (Spinner) findViewById(R.id.familyTreeLinesSpinner);
        mapTypeSpinner = (Spinner) findViewById(R.id.mapTypeSpinner);

        resync = (RelativeLayout)findViewById(R.id.resyncData);
        logout = (RelativeLayout)findViewById(R.id.logout);

        lifeStoryLinesSpinner.setSelection(0);
        spouseLinesSpinner.setSelection(1);
        familyTreeLinesSpinner.setSelection(2);

        showPastSettings();
        setSwitchListeners();
        setSpinnerListeners();
        setLayoutListeners();
    }

    private void showPastSettings() {
        lifeStoryLinesSwitch.setChecked(settings.isShowLifeStoryLines());
        spouseLinesSwitch.setChecked(settings.isShowSpouseLines());
        familyTreeLinesSwitch.setChecked(settings.isShowFamilyTreeLines());
    }
    private void setLayoutListeners() {
        resync.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                reloadData();
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                System.out.println("logout");
                Model.getInstance().reset();
                startMainActivity();

            }
        });
    }
    private void setSwitchListeners() {
        lifeStoryLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) settings.setShowLifeStoryLines(true);
                else settings.setShowLifeStoryLines(false);
            }
        });

        familyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) settings.setShowFamilyTreeLines(true);
                else settings.setShowFamilyTreeLines(false);
            }
        });

        spouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) settings.setShowSpouseLines(true);
                else settings.setShowSpouseLines(false);
            }
        });
    }

    private void setSpinnerListeners(){
        lifeStoryLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                settings.setLifeStoryLines(settings.getColors().get(selectedItem));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        familyTreeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                settings.setFamilyTreeLines(settings.getColors().get(selectedItem));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                settings.setSpouseLines(settings.getColors().get(selectedItem));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                settings.setMapType(settings.getMapTypes().get(selectedItem));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra("MyPerson", currPerson);
        startActivity(intent);
    }
    
    private void reloadData() {
        Model model = Model.getInstance();

        GetPeopleTask getPeople = new GetPeopleTask(this, model.getServerHost(), model.getServerPort());
        GetEventsTask getEvents = new GetEventsTask(this, model.getServerHost(), model.getServerPort());

        getPeople.setsActivity(this);
        getEvents.setsActivity(this);

        getPeople.execute(Model.getInstance().getAuthToken());
        getEvents.execute(Model.getInstance().getAuthToken());

//        startMainActivity();


    }

    private void loadMap() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);

//                intent.putExtra("MyPerson", currPerson);
        startActivity(intent);
    }

    public void setPeopleTask(boolean peopleTask) {
        this.peopleTask = peopleTask;
        if (peopleTask && eventTask) loadMap();
    }

    public void setEventsTask(boolean eventTask) {
        this.eventTask = eventTask;
        if (peopleTask && eventTask) loadMap();
    }
}
