package com.example.emilychandler.family_map.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by emilychandler on 11/22/17.
 */

public class PersonActivity extends AppCompatActivity {
    private ExpandableListView expandableList;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<Object>> listDataChild;

    private TextView firstName, lastName, gender;
    private Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);


        firstName = (TextView) findViewById(R.id.first_name);
        lastName = (TextView) findViewById(R.id.last_name);
        gender = (TextView) findViewById(R.id.gender);

        setPersonInfo();

        // get the listview
        expandableList = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expandableList.setAdapter(listAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    // Events
                    Event currEvent = (Event) listDataChild.get(groupPosition).get(childPosition);
                }
                else if (groupPosition == 1){
                    // Family
                    List<Object> objects = listDataChild.get("Family");
                    Person currPerson = (Person) objects.get(childPosition);
//                    Model.getInstance().setCurrPerson(currPerson);
                    startPersonActivity(currPerson);
                }
                return true;
            }
        });

    }
    public void startPersonActivity(Person currPerson){
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("MyPerson", currPerson);
        startActivity(intent);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<Object>>();

        // Adding child data
        listDataHeader.add("Life Events");
        listDataHeader.add("Family");

        // Adding child data
        List<Object> lifeEvents = new ArrayList<>();
        List<Object> family = new ArrayList<>();

        List<Event> event = Model.getInstance().getPersonEvents().get(person.getPersonId());
        for (int i = 0; i < event.size(); i++) {
            lifeEvents.add(event.get(i));
        }

        List<Person> personFamily = Model.getInstance().getPersonFamily(person);
        for (int i = 0; i < personFamily.size(); i++) {
            family.add(personFamily.get(i));
        }

        listDataChild.put(listDataHeader.get(0), lifeEvents);
        listDataChild.put(listDataHeader.get(1), family);
    }

    private void setPersonInfo() {
        person = (Person) getIntent().getSerializableExtra("MyPerson");
        Model.getInstance().setCurrPerson(person);

        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());

        if (person.getGender().equals("f")) gender.setText("Female");
        else gender.setText("Male");
    }



}
