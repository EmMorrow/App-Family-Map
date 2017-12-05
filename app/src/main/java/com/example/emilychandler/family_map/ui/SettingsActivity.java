package com.example.emilychandler.family_map.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.emilychandler.family_map.R;

/**
 * Created by emilychandler on 11/22/17.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch lifeStoryLinesSwitch = (Switch) findViewById(R.id.lifeStoryLinesSwitch);
        Switch spouseLinesSwitch = (Switch) findViewById(R.id.spouseLinesSwitch);
        Switch familyTreeLinesSwitch = (Switch) findViewById(R.id.familyTreeLinesSwitch);

        Spinner lifeStoryLinesSpinner = (Spinner) findViewById(R.id.lifeStoryLinesSpinner);
        Spinner spouseLinesSpinner = (Spinner) findViewById(R.id.spouseLinesSpinner);
        Spinner familyTreeLinesSpinner = (Spinner) findViewById(R.id.familyTreeLinesSpinner);



    }


}
