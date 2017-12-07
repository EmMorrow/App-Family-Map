package com.example.emilychandler.family_map.data;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emilychandler on 12/1/17.
 */

public class Settings {

    public enum MapType {
        NORMAL, HYBRID, SATELLITE, TERRAIN
    }

    private Integer lifeStoryLines;
    private Integer familyTreeLines;
    private Integer spouseLines;
    private Integer mapType;
    private boolean showLifeStoryLines, showFamilyTreeLines, showSpouseLines;
    private Map<String, Integer> colors;
    private Map<String, Integer> mapTypes;
//    private Map<String, MapType> mapTypes;

    public Settings(){
        lifeStoryLines = Color.BLACK;
        familyTreeLines = Color.CYAN;
        spouseLines = Color.BLUE;
        showLifeStoryLines = false;
        showFamilyTreeLines = false;
        showSpouseLines = false;
        mapType = GoogleMap.MAP_TYPE_TERRAIN;
    }

    public boolean isShowLifeStoryLines() {
        return showLifeStoryLines;
    }

    public void setShowLifeStoryLines(boolean showLifeStoryLines) {
        this.showLifeStoryLines = showLifeStoryLines;
    }

    public boolean isShowFamilyTreeLines() {
        return showFamilyTreeLines;
    }

    public void setShowFamilyTreeLines(boolean showFamilyTreeLines) {
        this.showFamilyTreeLines = showFamilyTreeLines;
    }

    public boolean isShowSpouseLines() {
        return showSpouseLines;
    }

    public void setShowSpouseLines(boolean showSpouseLines) {
        this.showSpouseLines = showSpouseLines;
    }

    public Integer getLifeStoryLines() {
        return lifeStoryLines;
    }

    public void setLifeStoryLines(Integer lifeStoryLines) {
        this.lifeStoryLines = lifeStoryLines;
    }

    public Integer getFamilyTreeLines() {
        return familyTreeLines;
    }

    public void setFamilyTreeLines(Integer familyTreeLines) {
        this.familyTreeLines = familyTreeLines;
    }

    public Integer getSpouseLines() {
        return spouseLines;
    }

    public void setSpouseLines(Integer spouseLines) {
        this.spouseLines = spouseLines;
    }

    public Integer getMapType() {
        return mapType;
    }

    public void setMapType(Integer mapType) {
        this.mapType = mapType;
    }

    public Map<String, Integer> getColors() {
        if (colors == null) {
            colors = new HashMap<>();
            colors.put("Black", Color.BLACK);
            colors.put("Blue", Color.BLUE);
            colors.put("Cyan", Color.CYAN);
            colors.put("Green", Color.GREEN);
            colors.put("Magenta", Color.MAGENTA);
            colors.put("Red", Color.RED);
        }
        return colors;
    }

    public Map<String, Integer> getMapTypes() {
        if (mapTypes == null) {
            mapTypes = new HashMap<>();
            mapTypes.put("Normal", GoogleMap.MAP_TYPE_NORMAL);
            mapTypes.put("Satellite", GoogleMap.MAP_TYPE_SATELLITE);
            mapTypes.put("Terrain", GoogleMap.MAP_TYPE_TERRAIN);
            mapTypes.put("Hybrid", GoogleMap.MAP_TYPE_HYBRID);
        }
        return mapTypes;
    }

//    public Map<String, MapType> getMapTypes() {
//        if (mapTypes == null) {
//            mapTypes = new HashMap<>();
//            mapTypes.put("Normal", MapType.NORMAL);
//            mapTypes.put("Satellite", MapType.SATELLITE);
//            mapTypes.put("Terrain", MapType.TERRAIN);
//            mapTypes.put("Hybrid", MapType.HYBRID);
//        }
//        return mapTypes;
//    }
}
