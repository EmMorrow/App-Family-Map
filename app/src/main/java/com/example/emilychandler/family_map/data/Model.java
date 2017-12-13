package com.example.emilychandler.family_map.data;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by emilychandler on 11/11/17.
 */

public class Model {
    private static Model instance = null;
    private Map<String, Person> people;
    private Map<String, Event> events;
    private Map<String, List<Event>> personEvents;
    private List<String> eventTypes;


    private String authToken, serverHost, serverPort;
    private Person currPerson;
    private Person rootPerson;

    private Settings settings;
    private Filter filter;

    protected Model() {
        settings = new Settings();
        filter = new Filter();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public Person getRootPerson() {
        return rootPerson;
    }

    public void setRootPerson(Person rootPerson) {
        this.rootPerson = rootPerson;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes() {
        if (eventTypes == null) {
            System.out.println("set event types");
            eventTypes = new ArrayList<>();
            for (Event myEvent : events.values()) {
                String eventType = myEvent.getEventType();
                eventType = eventType.toLowerCase();
                System.out.println(eventType);
                if (!eventTypes.contains(eventType)) {
                    eventTypes.add(eventType);
                }
            }
            filter.setFEventTypes(eventTypes);
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public Filter getFilter() {
        return filter;
    }

    public Person getCurrPerson() {
        return currPerson;
    }

    public void setCurrPerson(Person currPerson) {
        this.currPerson = currPerson;
    }

    public Map<String, List<Event>> getPersonEvents() {
        if (personEvents == null) {
            addPersonEvents();
        }
        return personEvents;
    }

    public void addPersonEvents() {
        Map<String, Event> events = Model.getInstance().getEvents();
        personEvents = new HashMap<>();

        if (events == null) return;

        for (Event event : events.values()) {
            List<Event> currEvents = personEvents.get(event.getPerson());

            if (currEvents == null) {
                currEvents = new ArrayList<>();
                personEvents.put(event.getPerson(), currEvents);
            }
            currEvents.add(event);
        }
        sortEvents();
    }

    private void sortEvents() {
        Comparator<Event> comp = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getYear().compareTo(o2.getYear());
            }
        };
        for (List<Event> myEvents: personEvents.values()) {
            Collections.sort(myEvents, comp);
        }
    }

    public void setPersonEvents(Map<String, List<Event>> personEvents) {
        this.personEvents = personEvents;
    }

    public void setPeople(Map<String, Person> people) {
        this.people = people;
    }

    public Map<String, Person> getPeople() {
        return people;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setEvents(Map<String, Event> events) {
        System.out.println("set Events");
        this.events = events;
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public void reset() {
        instance = new Model();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<Person> getPersonFamily(Person person) {
        List<Person> family = new ArrayList<>();

        Person mother = people.get(person.getMother());
        Person father = people.get(person.getFather());
        Person spouse = people.get(person.getSpouse());
        if (mother != null && father != null) {
            family.add(mother);
            family.add(father);
        }

        if(spouse!=null) family.add(spouse);

        for (Person currPerson : people.values()) {
            if (currPerson.getMother() == null || currPerson.getFather() == null) continue;
            if (currPerson.getMother().equals(person.getPersonId()) || currPerson.getFather().equals(person.getPersonId())) {
                family.add(currPerson);
            }
        }
        return family;
    }

    public Map<String,Event> getFathersSide() {
        getPersonEvents();
        Map<String,Event> myEvents = new HashMap<>();

        List<Event> ancestorEvents = personEvents.get(rootPerson.getPersonId());
        if (ancestorEvents == null) return null;
        for (int i = 0; i < ancestorEvents.size(); i++) {
            Event event = ancestorEvents.get(i);
            myEvents.put(event.getEventId(), event);
        }

        if (rootPerson.getFather() != null) getSide(myEvents,people.get(rootPerson.getFather()));
        return myEvents;
    }

    public Map<String,Event> getMothersSide() {
        getPersonEvents();
        Map<String,Event> myEvents = new HashMap<>();

        List<Event> ancestorEvents = personEvents.get(rootPerson.getPersonId());
        if (ancestorEvents == null) return null;
        for (int i = 0; i < ancestorEvents.size(); i++) {
            Event event = ancestorEvents.get(i);
            myEvents.put(event.getEventId(), event);
        }

        getSide(myEvents,people.get(rootPerson.getMother()));
        return myEvents;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void getSide(Map<String,Event> myEvents, Person ancestor) {
        if (ancestor == null) return;
        List<Event> ancestorEvents = personEvents.get(ancestor.getPersonId());

        if(ancestorEvents != null) {
            for (int i = 0; i < ancestorEvents.size(); i++) {
                Event event = ancestorEvents.get(i);
                myEvents.put(event.getEventId(), event);
            }
        }

        if(ancestor.getFather() == null) getSide(myEvents,null);
        else getSide(myEvents,people.get(ancestor.getFather()));

        if(ancestor.getMother() == null) getSide(myEvents,null);
        else getSide(myEvents,people.get(ancestor.getMother()));
    }

    public Map<String,Event> getMaleEvents() {
        Map<String,Event> maleEvents = new HashMap<>();

       for(Event event : events.values()){
           if(people.get(event.getPerson()).getGender().equals("m")){
               maleEvents.put(event.getEventId(), event);
           }
       }
        return maleEvents;
    }

    public Map<String,Event> getFemaleEvents() {
        Map<String,Event> femaleEvents = new HashMap<>();

        for(Event event : events.values()){
            if(people.get(event.getPerson()).getGender().equals("f")){
                femaleEvents.put(event.getEventId(), event);
            }
        }
        return femaleEvents;
    }

    public Map<String,Event> getEventTypeEvents(String eventType) {
        Map<String,Event> newEvents = new HashMap<>();

        for(Event event : events.values()){
            if(event.getEventType().toLowerCase().equals(eventType)){
                newEvents.put(event.getEventId(), event);
            }
        }
        return newEvents;
    }

}
