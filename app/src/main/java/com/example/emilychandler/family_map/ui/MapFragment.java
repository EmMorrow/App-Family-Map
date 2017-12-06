package com.example.emilychandler.family_map.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.Settings;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by emilychandler on 11/15/17.
 */

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback{
    private GoogleMap myMap;
    private MapView mapView;

    private Settings settings;
    private Polyline spouseLine;

    private TextView linkToPerson;

    private List<Polyline> polylines = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_map,container,false);
        linkToPerson = (TextView) v.findViewById(R.id.name);
        mapView = (MapView)v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        linkToPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                startActivity(intent);
            }
        });

        mapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map, menu);
        menu.findItem(R.id.filter).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_filter)
                .actionBarSize()
                .color(Color.WHITE));
        menu.findItem(R.id.search).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_search)
                        .actionBarSize()
                        .color(Color.WHITE));
        menu.findItem(R.id.settings).setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear)
                        .actionBarSize()
                        .color(Color.WHITE));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.filter :
                break;
            case R.id.search :
                break;
            case R.id.settings :
                break;
            default :
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        fillPersonEvents();
        settings = Model.getInstance().getSettings();
        myMap = googleMap;

        switch (settings.getMapType()){
            case NORMAL:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case TERRAIN:
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case SATELLITE:
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case HYBRID:
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }

        addMarkers(myMap);

        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                clearPolylines();
                Event event = (Event) marker.getTag();
                displayEvent(event);
                drawLifeStoryLines(event);
                if (settings.getSpouseLines() != null) drawSpouseLines(event);
                if (settings.getFamilyTreeLines() != null) drawFamilyTreeLines(event);
                if (settings.getLifeStoryLines() != null) drawLifeStoryLines(event);
                return false;
            }
        });
    }

    private void addMarkers(GoogleMap myMap) {
        ArrayList<Event> myEvents = Model.getInstance().getMyevents();
        for (int i = 0; i < myEvents.size(); i++) {
            Event myEvent = myEvents.get(i);
            LatLng location = new LatLng(myEvent.getLatitude(), myEvent.getLongitude());

            Marker myMarker = null;
            if (myEvent.getEventType().equals("birth")) {
                myMarker = myMap.addMarker(new MarkerOptions()
                        .position(location)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
            else if (myEvent.getEventType().equals("marriage")) {
                myMarker = myMap.addMarker(new MarkerOptions()
                        .position(location)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            }
            else if (myEvent.getEventType().equals("death")) {
                myMarker = myMap.addMarker(new MarkerOptions()
                        .position(location)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            }
            myMarker.setTag(myEvent);
        }
    }

    private void displayEvent(Event currEvent){
        Model m = Model.getInstance();
        Map<String, Person> people;

        people = m.getPeople();
        Person currPerson = people.get(currEvent.getPerson());

        TextView name = (TextView) getActivity().findViewById(R.id.name);
        name.setText(currPerson.getFirstName() + " " + currPerson.getLastName());

        TextView eventInfo = (TextView) getActivity().findViewById(R.id.eventInfo);
        eventInfo.setText(currEvent.getEventType() + ": ");
        eventInfo.append(currEvent.getCity() + ", ");
        eventInfo.append(currEvent.getCountry() + " (");
        eventInfo.append(currEvent.getYear() + ")");

        IconTextView image = (IconTextView) getActivity().findViewById(R.id.image);
        if(currPerson.getGender().equals("m")) {
            image.setText("{fa-male}");
            image.setTextColor(Color.BLUE);
        }
        else {
            image.setText("{fa-female}");
            image.setTextColor(Color.MAGENTA);
        }

    }

    private void drawSpouseLines(Event event) {
        Map<String,Person> people = Model.getInstance().getPeople();
        Map<String, List<Event>> personEvents = Model.getInstance().getPersonEvents();

        Person currPerson = people.get(event.getPerson());
        String spouseId = currPerson.getSpouse();

        if (spouseId != null) {
            List<Event> events = personEvents.get(spouseId);
            Event spouseBirth = null;
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getEventType().equals("birth")) spouseBirth = events.get(i);
            }

            spouseLine = myMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(event.getLatitude(), event.getLongitude()), new LatLng(spouseBirth.getLatitude(), spouseBirth.getLongitude()))
                    .width(9)
                    .color(Color.BLUE));

            polylines.add(spouseLine);

        }


    }

    private void drawFamilyTreeLines(Event event) {
        Person person = Model.getInstance().getPeople().get(event.getPerson());

        if (person.getMother() != null && person.getFather() != null) {
            Event mBirth = getBirthEvent(Model.getInstance().getPersonEvents().get(person.getMother()));
            Event fBirth = getBirthEvent(Model.getInstance().getPersonEvents().get(person.getFather()));

            familyTree(event, mBirth, fBirth, 25);
        }
    }

    private void drawLifeStoryLines(Event event) {
        List<Event> currEvents = Model.getInstance().getPersonEvents().get(event.getPerson());

        Event birth = null, marriage = null, death = null;
        for (int i = 0; i < currEvents.size(); i++) {
            if (currEvents.get(i).getEventType().equals("birth")) birth = currEvents.get(i);
            else if(currEvents.get(i).getEventType().equals("marriage")) marriage = currEvents.get(i);
            else if(currEvents.get(i).getEventType().equals("death")) death = currEvents.get(i);
        }
        if (marriage != null) {
            Polyline bLine = myMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(birth.getLatitude(), birth.getLongitude()), new LatLng(marriage.getLatitude(), marriage.getLongitude()))
                    .width(5)
                    .color(Color.BLUE));
            polylines.add(bLine);

            if (death != null) {
                Polyline mLine = myMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(marriage.getLatitude(), marriage.getLongitude()), new LatLng(death.getLatitude(), death.getLongitude()))
                        .width(5)
                        .color(Color.BLUE));
                polylines.add(mLine);
            }
        }
    }

    private void familyTree (Event currEvent, Event mEvent, Event fEvent, int lineWidth) {
        if (mEvent == null || fEvent == null) return;

        Polyline mLine = myMap.addPolyline(new PolylineOptions()
                .add(new LatLng(currEvent.getLatitude(), currEvent.getLongitude()), new LatLng(mEvent.getLatitude(), mEvent.getLongitude()))
                .width(lineWidth)
                .color(Color.BLACK));

        Polyline fLine = myMap.addPolyline(new PolylineOptions()
                .add(new LatLng(currEvent.getLatitude(), currEvent.getLongitude()), new LatLng(fEvent.getLatitude(), fEvent.getLongitude()))
                .width(lineWidth)
                .color(Color.BLACK));

        polylines.add(mLine);
        polylines.add(fLine);

        Map<String, Person> people = Model.getInstance().getPeople();
        Person mother = people.get(mEvent.getPerson());
        Person mMother = people.get(mother.getMother());
        Person mFather = people.get(mother.getFather());

        Person father = people.get(fEvent.getPerson());
        Person fMother = people.get(father.getMother());
        Person fFather = people.get(father.getFather());

        Map<String, List<Event>> personEvents = Model.getInstance().getPersonEvents();
        if (mMother == null || mFather == null) familyTree(mEvent, null, null, lineWidth-5);
        else {
            Event mMotherBirth = getBirthEvent(personEvents.get(mMother.getPersonId()));
            Event mFatherBirth = getBirthEvent(personEvents.get(mFather.getPersonId()));
            familyTree(mEvent, mMotherBirth, mFatherBirth, lineWidth-5);
        }

        if (fMother == null || mFather == null) familyTree(fEvent, null, null, lineWidth-5);
        else {
            Event fMotherBirth = getBirthEvent(personEvents.get(fMother.getPersonId()));
            Event fFatherBirth = getBirthEvent(personEvents.get(fFather.getPersonId()));
            familyTree(fEvent, fMotherBirth, fFatherBirth, lineWidth-5);
        }



    }

    private Event getBirthEvent(List<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventType().equals("birth")) return events.get(i);
        }
        return null;
    }

    private void fillPersonEvents() {
        Map<String, Event> events = Model.getInstance().getEvents();
        Map<String, List<Event>> personEvents = new HashMap<>();

        List<Event> currEvents;

        for (Event event : events.values()) {
            currEvents = personEvents.get(event.getPerson());
            if (currEvents != null) currEvents.add(event);
            else {
                currEvents = new ArrayList<>();
                currEvents.add(event);
                personEvents.put(event.getPerson(), currEvents);
            }
        }

        Model.getInstance().setPersonEvents(personEvents);
    }

    private void clearPolylines() {
        for (int i = 0; i < polylines.size(); i++) {
            polylines.get(i).remove();
        }
        polylines.clear();
    }
}
