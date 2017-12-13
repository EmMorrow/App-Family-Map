package com.example.emilychandler.family_map.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Filter;
import com.example.emilychandler.family_map.data.Person;

import java.util.List;
import java.util.Set;

/**
 * Created by emilychandler on 11/22/17.
 */

public class FilterActivity extends AppCompatActivity{
    private Switch fatherSideSwitch, motherSideSwitch;
    private Switch femaleEventsSwitch, maleEventsSwitch;
    private ListView listView;
    
    private Filter filter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        filter = Model.getInstance().getFilter();

        List<String> eventList = Model.getInstance().getEventTypes();
        String[] eventArray = new String[eventList.size()];
        eventArray = eventList.toArray(eventArray);

        ListAdapter adapter = new ListAdapter(this, eventArray);
        fatherSideSwitch = (Switch) findViewById(R.id.fatherSideSwitch);
        motherSideSwitch = (Switch) findViewById(R.id.motherSizeSwitch);
        femaleEventsSwitch = (Switch) findViewById(R.id.femaleEventSwitch);
        maleEventsSwitch = (Switch) findViewById(R.id.maleEventSwitch);
        
        listView = (ListView) findViewById(R.id.filterList);
        listView.setAdapter(adapter);
        showPastSettings();
        setListeners();
    }

    private void showPastSettings() {
        fatherSideSwitch.setChecked(filter.isFathersSide());
        motherSideSwitch.setChecked(filter.isMothersSide());
        femaleEventsSwitch.setChecked(filter.isFemaleEvents());
        maleEventsSwitch.setChecked(filter.isMaleEvents());
    }

    private void setListeners() {
        fatherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) filter.setFathersSide(true);
                else filter.setFathersSide(false);
            }
        });

        motherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) filter.setMothersSide(true);
                else filter.setMothersSide(false);
            }
        });

        femaleEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) filter.setFemaleEvents(true);
                else filter.setFemaleEvents(false);
            }
        });

        maleEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) filter.setMaleEvents(true);
                else filter.setMaleEvents(false);
            }
        });

//        listVi
    }

}
