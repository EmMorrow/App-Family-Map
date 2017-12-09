package com.example.emilychandler.family_map.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by emilychandler on 11/11/17.
 */

public class Model {
    private static Model instance = null;
    private ArrayList<Person> mypeople;
    private ArrayList<Event> myevents;
    private Map<String, Person> people;
    private Map<String, Event> events;
    private Map<String, List<Event>> personEvents;
    private Set<String> eventTypes;

    private String authToken;
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

    public Set<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(Set<String> eventTypes) {
        this.eventTypes = eventTypes;
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
        Comparator<Event> comp = new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getYear().compareTo(o2.getYear());
            }
        };

        if (personEvents == null) {
            Map<String, Event> events = Model.getInstance().getEvents();
            Map<String, List<Event>> personEvents = new HashMap<>();

            List<Event> currEvents;
            if (events != null) {
                for (Event event : events.values()) {
                    currEvents = personEvents.get(event.getPerson());
                    if (currEvents!= null) Collections.sort(currEvents, comp);

                    if (currEvents == null) {
                        currEvents = new ArrayList<>();
                        personEvents.put(event.getPerson(), currEvents);
                    }
                    currEvents.add(event);
                }
                Model.getInstance().setPersonEvents(personEvents);
            }
        }
        return personEvents;
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

    public void setEvents(Map<String, Event> events) {
        this.events = events;
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public void setMypeople(ArrayList<Person> mypeople) {
        this.mypeople = mypeople;
    }

    public ArrayList<Person> getMypeople() {
        return mypeople;
    }

    public void setMyevents(ArrayList<Event> myevents) {
        this.myevents = myevents;
    }

    public ArrayList<Event> getMyevents() {
        return myevents;
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
        if (mother != null && father != null) {
            family.add(mother);
            family.add(father);
        }

        for (Person currPerson : people.values()) {
            if (currPerson.getMother() == null || currPerson.getFather() == null) continue;
            if (currPerson.getMother().equals(person.getPersonId()) || currPerson.getFather().equals(person.getPersonId())) {
                family.add(currPerson);
            }
        }
        return family;
    }

    public Map<String,Event> getFathersSide() {
        Map<String,Event> myEvents = new HashMap<>();
        getSide(myEvents,people.get(rootPerson.getFather()));
        return myEvents;
    }

    public Map<String,Event> getMothersSide() {
        Map<String,Event> myEvents = new HashMap<>();
        getSide(myEvents,people.get(rootPerson.getMother()));
        return myEvents;
    }

    public void getSide(Map<String,Event> myEvents, Person ancestor) {
        List<Event> ancestorEvents = personEvents.get(ancestor.getPersonId());

        for (int i = 0; i < ancestorEvents.size(); i++) {
            Event event = ancestorEvents.get(i);
            myEvents.put(event.getEventId(), event);
        }
        getSide(myEvents,people.get(ancestor.getFather()));
        getSide(myEvents,people.get(ancestor.getMother()));
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

    public Map<String,Event> getFemaleEvents(Person person) {
        Map<String,Event> femaleEvents = new HashMap<>();

        for(Event event : events.values()){
            if(people.get(event.getPerson()).getGender().equals("m")){
                femaleEvents.put(event.getEventId(), event);
            }
        }
        return femaleEvents;
    }

}
