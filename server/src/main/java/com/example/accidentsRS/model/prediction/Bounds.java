package com.example.accidentsRS.model.prediction;

import java.util.List;

public class Bounds {

    public static final String COORDINATES = "coordinates";
    private List<List<List<Float>>> coordinates;
    public static final String TYPE = "type";
    private String type = "Polygon";

    public Bounds() {
    }

    public Bounds(List<List<List<Float>>> coordinates, String type) {
        this.coordinates = coordinates;
        this.type = type;
    }

    public List<List<List<Float>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Float>>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
