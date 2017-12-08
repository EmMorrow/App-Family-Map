package com.example.emilychandler.family_map.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Model;

import java.util.Set;

/**
 * Created by emilychandler on 11/22/17.
 */

public class FilterActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        String[] eventArray = setToArray(Model.getInstance().getEventTypes());
        ListAdapter adapter = new ListAdapter(this, eventArray);

        ListView listView = (ListView) findViewById(R.id.filterList);
        listView.setAdapter(adapter);
    }

    private String[] setToArray(Set<String> set) {
        String[] array = new String[set.size()];
        int i = 0;
        for (String curr : set) {
            array[i] = curr;
            i++;
        }
        return array;
    }
}
