package com.example.accidentsRS.data;

import com.example.accidentsRS.model.Location;

public class DirectionalStreetData {

    private String externalId;
    private String directionalId;
    private String name;
    private Location location;
    private Float length;
    private String source_intersection_id;
    private String destination_intersection_id;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getDirectionalId() {
        return directionalId;
    }

    public void setDirectionalId(String directionalId) {
        this.directionalId = directionalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public String getSource_intersection_id() {
        return source_intersection_id;
    }

    public void setSource_intersection_id(String source_intersection_id) {
        this.source_intersection_id = source_intersection_id;
    }

    public String getDestination_intersection_id() {
        return destination_intersection_id;
    }

    public void setDestination_intersection_id(String destination_intersection_id) {
        this.destination_intersection_id = destination_intersection_id;
    }
}
