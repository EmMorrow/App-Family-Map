package com.example.emilychandler.family_map.data;

import java.util.List;

/**
 * Created by emilychandler on 12/1/17.
 */

public class Filter {
    private boolean fathersSide;
    private boolean mothersSide;
    private boolean femaleEvents;
    private boolean maleEvents;
    private List<String> feventTypes;


    public Filter() {
        fathersSide = true;
        mothersSide = true;
        femaleEvents = true;
        maleEvents = true;
    }

    public List<String> getFEventTypes() {
        return feventTypes;
    }

    public void setFEventTypes(List<String> feventTypes) {
        this.feventTypes = feventTypes;
    }

    public void addEventType(String eventType) {
        if (!feventTypes.contains(eventType)) feventTypes.add(eventType);
    }

    public void removeEventType(String eventType) {
        if (feventTypes.contains(eventType)) {
            feventTypes.remove(eventType);
        }
    }

    public boolean isMothersSide() {
        return mothersSide;
    }

    public void setMothersSide(boolean mothersSide) {
        this.mothersSide = mothersSide;
    }

    public boolean isFemaleEvents() {
        return femaleEvents;
    }

    public void setFemaleEvents(boolean femaleEvents) {
        this.femaleEvents = femaleEvents;
    }

    public boolean isMaleEvents() {
        return maleEvents;
    }

    public void setMaleEvents(boolean maleEvents) {
        this.maleEvents = maleEvents;
    }

    public boolean isFathersSide() {
        return fathersSide;
    }

    public void setFathersSide(boolean fathersSide) {
        this.fathersSide = fathersSide;
    }
}
