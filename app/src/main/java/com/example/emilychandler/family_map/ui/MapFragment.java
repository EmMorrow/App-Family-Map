package com.example.emilychandler.family_map.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.Serializable;

import com.example.emilychandler.family_map.R;
import com.example.emilychandler.family_map.data.Event;
import com.example.emilychandler.family_map.data.Filter;
import com.example.emilychandler.family_map.data.Model;
import com.example.emilychandler.family_map.data.Person;
import com.example.emilychandler.family_map.data.Settings;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import static com.example.emilychandler.family_map.data.Settings.MapType.NORMAL;
import static com.example.emilychandler.family_map.data.Settings.MapType.SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_MAGENTA;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ROSE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;


/**
 * Created by emilychandler on 11/15/17.
 */

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback{
    private GoogleMap myMap;
    private MapView mapView;
    private Settings settings;
    private Polyline spouseLine;
    private Event currEvent;
    private boolean firstCall = true;

    private Filter filter;
    private TextView linkToPerson;
    private Person currPerson;
    private Map<String, Event> myEvents;
    private Map<String,Float> eventColors = null;
    private List<Polyline> polylines = new ArrayList<>();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (currPerson == null) {

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
        else System.out.println("RIGHT HERE" + currPerson.getFirstName());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.filter :
                startActivity(new Intent(getActivity(), FilterActivity.class));
                break;
            case R.id.search :
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.settings :
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            default :
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = Model.getInstance().getSettings();

        currEvent = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currEvent =(Event) bundle.get("MyEvent");
            setHasOptionsMenu(false);
        }
        else setHasOptionsMenu(true);
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
                intent.putExtra("MyPerson", currPerson);
                startActivity(intent);
            }
        });

        mapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

        myMap = googleMap;
//        myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        setMaptype();
        myMap.setMapType(settings.getMapType());
//        Model.getInstance().setEventTypes();
        Model.getInstance().setEventTypes();
        filter = Model.getInstance().getFilter();

        addMarkers(myMap);

        if(currEvent != null) {
            displayEvent(currEvent);
            centerMap(currEvent);
        }


        setMarkerListener();

    }

    @Override
    public void onResume() {
        mapView.onResume();
        setMaptype();
        if (!firstCall) {
            myMap.clear();
            addMarkers(myMap);
        }
        firstCall = false;
        super.onResume();
    }

    private void setMarkerListener() {
        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                clearPolylines();
                Event event = (Event) marker.getTag();
                currPerson = Model.getInstance().getPeople().get(event.getPerson());
                displayEvent(event);
                if (settings.isShowSpouseLines()) drawSpouseLines(event);
                if (settings.isShowFamilyTreeLines()) drawFamilyTreeLines(event);
                if (settings.isShowLifeStoryLines()) drawLifeStoryLines(event);
                return false;
            }
        });
    }

    private Map<String, Event> getFilterEvents() {
        myEvents = new HashMap<>();
        if (filter.isFathersSide()) {
            Map<String, Event> fathersSide = Model.getInstance().getFathersSide();
            myEvents = addToMap(fathersSide,myEvents);
        }

        if (filter.isMothersSide()) {
            Map<String, Event> mothersSide = Model.getInstance().getMothersSide();
            myEvents = addToMap(mothersSide,myEvents);
        }

        if (filter.isFemaleEvents()) {
            Map<String, Event> femaleEvents = Model.getInstance().getFemaleEvents();
            myEvents = addToMap(femaleEvents,myEvents);
        }

        if(filter.isMaleEvents()) {
            Map<String, Event> maleEvents = Model.getInstance().getMaleEvents();
            myEvents = addToMap(maleEvents,myEvents);
        }

        for (String eventType: Model.getInstance().getFilter().getFEventTypes()) {
            System.out.println("Before " + myEvents.size());
            Map<String, Event> typeEvents = Model.getInstance().getEventTypeEvents(eventType);
            myEvents = addToMap(typeEvents,myEvents);
            System.out.println("After " + myEvents.size());
        }
        return myEvents;
    }

    private Map<String,Event> addToMap(Map<String,Event> originalMap, Map<String,Event> newMap) {
        if (originalMap == null) return null;
        for (Map.Entry<String, Event> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            Event value = entry.getValue();

            newMap.put(key,value);
        }
        return newMap;
    }

    private void addMarkers(GoogleMap myMap) {
        myEvents = getFilterEvents();

        Set<String> eventTypes = new HashSet<>();
        eventColors = settings.getEventColors();

        if (settings.getEventColors() == null) eventColors = new HashMap<>();
        Stack<Float> colors = getColorStack();

        for (Event myEvent : myEvents.values()) {
            String eventType = myEvent.getEventType().toLowerCase();
            LatLng location = new LatLng(myEvent.getLatitude(), myEvent.getLongitude());
            eventTypes.add(eventType);

            Float eventColor = eventColors.get(eventType);
            if (eventColor == null) {
                eventColor = colors.pop();
                eventColors.put(eventType, eventColor);
            }

            addMarker(location, eventColor, myEvent);
        }

//        Model.getInstance().setEventTypes(eventTypes);

        settings.setEventColors(eventColors);
        Model.getInstance().setSettings(settings); // What is this
    }

    private void addMarker(LatLng location, Float eventColor, Event myEvent) {
        Marker myMarker = myMap.addMarker(new MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.defaultMarker(eventColor)));
        myMarker.setTag(myEvent);
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

    private void setMaptype() {
        if(settings == null) return;
        if (myMap == null) return;

        switch (settings.getMapType()){
            case MAP_TYPE_NORMAL:
                myMap.setMapType(MAP_TYPE_NORMAL);
                break;
            case MAP_TYPE_HYBRID:
                myMap.setMapType(MAP_TYPE_HYBRID);
                break;
            case MAP_TYPE_SATELLITE:
                myMap.setMapType(MAP_TYPE_SATELLITE);
                break;
            case MAP_TYPE_TERRAIN:
                myMap.setMapType(MAP_TYPE_TERRAIN);
                break;
        }
    }

    private void centerMap(Event event) {
        LatLng eventLocation = new LatLng(event.getLatitude(),event.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLng(eventLocation);
        myMap.moveCamera(update);
        zoomMap(10);
    }

    private void zoomMap(float amount) {
        CameraUpdate update = CameraUpdateFactory.zoomTo(amount);
        myMap.moveCamera(update);
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
                    .color(settings.getSpouseLines()));

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

        for (int i = 0; i < currEvents.size(); i++) {
            Event event1 = currEvents.get(i);
            Event event2 = null;
            if (currEvents.size() > i+1) event2 = currEvents.get(i+1);

            if (event2 != null) {
                Polyline bLine = myMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(event1.getLatitude(), event1.getLongitude()), new LatLng(event2.getLatitude(), event2.getLongitude()))
                        .width(5)
                        .color(settings.getLifeStoryLines()));
                polylines.add(bLine);
            }
        }
    }

    private void familyTree (Event currEvent, Event mEvent, Event fEvent, int lineWidth) {
        if (mEvent == null || fEvent == null) return;
        if (myEvents.get(currEvent.getEventId()) == null) return;

        if (myEvents.get(mEvent.getEventId()) != null) {
            Polyline mLine = myMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(currEvent.getLatitude(), currEvent.getLongitude()), new LatLng(mEvent.getLatitude(), mEvent.getLongitude()))
                    .width(lineWidth)
                    .color(settings.getFamilyTreeLines()));
            polylines.add(mLine);
        }

        if(myEvents.get(fEvent.getEventId()) != null) {
            Polyline fLine = myMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(currEvent.getLatitude(), currEvent.getLongitude()), new LatLng(fEvent.getLatitude(), fEvent.getLongitude()))
                    .width(lineWidth)
                    .color(settings.getFamilyTreeLines()));
            polylines.add(fLine);
        }

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

    private void clearPolylines() {
        for (int i = 0; i < polylines.size(); i++) {
            polylines.get(i).remove();
        }
        polylines.clear();
    }

    private Stack<Float> getColorStack() {
        Stack<Float> colors = new Stack<>();
        colors.push(HUE_BLUE);
        colors.push(HUE_CYAN);
        colors.push(HUE_GREEN);
        colors.push(HUE_MAGENTA);
        colors.push(HUE_RED);
        colors.push(HUE_AZURE);
        colors.push(HUE_ROSE);
        colors.push(HUE_VIOLET);
        return colors;
    }
}
