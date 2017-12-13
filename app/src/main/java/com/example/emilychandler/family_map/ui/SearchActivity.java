package com.example.emilychandler.family_map.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by emilychandler on 11/22/17.
 */

public class SearchActivity extends AppCompatActivity{
    private SearchView searchView;
    private SearchListAdapter peopleAdapter;
    private EventSearchListAdapter eventAdapter;
    private ListView personList, eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.search);

        peopleAdapter = new SearchListAdapter(this, mapToList(Model.getInstance().getPeople()));
        eventAdapter = new EventSearchListAdapter(this, emapToList(Model.getInstance().getEvents()));

        searchView.setQueryHint("Search");
        personList = (ListView) findViewById(R.id.peopleResults);
        eventList = (ListView) findViewById(R.id.eventResults);
        personList.setAdapter(peopleAdapter);
        eventList.setAdapter(eventAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                peopleAdapter.getFilter().filter(newText);
                eventAdapter.getFilter().filter(newText);
                return false;
            }
        });

        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = (Person) personList.getItemAtPosition(position);
                startPersonActivity(person);
            }
        });

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) eventList.getItemAtPosition(position);
                startMapActivity(event);
            }
        });
    }
    public void onEventClick(Event event) {
        Toast.makeText(this, event.getEventType(), Toast.LENGTH_SHORT).show();
    }
    public void startPersonActivity(Person currPerson){
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("MyPerson", currPerson);
        startActivity(intent);
    }

    public void startMapActivity(Event currEvent) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("MyEvent", currEvent);
        startActivity(intent);
    }

    private List<Person> mapToList(Map<String,Person> map) {
        List<Person> list = new ArrayList<>();
        for (Person obj : map.values()) {
            list.add(obj);
        }
        return list;
    }

    private List<Event> emapToList(Map<String,Event> map) {
        List<Event> list = new ArrayList<>();
        for (Event obj : map.values()) {
            list.add(obj);
        }
        return list;
    }
}
