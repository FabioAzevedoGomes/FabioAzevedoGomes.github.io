package com.example.accidentsRS.data;

import com.example.accidentsRS.model.Location;

import java.util.List;

public class IntersectionData {

    private String externalId;
    private Location location;
    private List<String> connected_street_ids;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getConnected_street_ids() {
        return connected_street_ids;
    }

    public void setConnected_street_ids(List<String> connected_street_ids) {
        this.connected_street_ids = connected_street_ids;
    }
}
