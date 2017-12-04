package com.example.emilychandler.family_map.data;

/**
 * Created by emilychandler on 12/1/17.
 */

public class Settings {
    public enum MapType {
        NORMAL, HYBRID, SATELLITE, TERRAIN
    }
    private String lifeStoryLines;
    private String familyTreeLines;
    private String spouseLines;
    private MapType mapType;

    public Settings(){
        lifeStoryLines = null;
        familyTreeLines = null;
        spouseLines = null;
        mapType = MapType.NORMAL;
    }

    public String getLifeStoryLines() {
        return lifeStoryLines;
    }

    public void setLifeStoryLines(String lifeStoryLines) {
        this.lifeStoryLines = lifeStoryLines;
    }

    public String getFamilyTreeLines() {
        return familyTreeLines;
    }

    public void setFamilyTreeLines(String familyTreeLines) {
        this.familyTreeLines = familyTreeLines;
    }

    public String getSpouseLines() {
        return spouseLines;
    }

    public void setSpouseLines(String spouseLines) {
        this.spouseLines = spouseLines;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }
}
