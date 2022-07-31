package com.example.accidentsRS.data;

import com.example.accidentsRS.model.Location;

import java.util.List;

public class IntersectionData {

    private String externalId;
    private Location location;
    private List<String> connected_street_ids;
    private List<String> incoming_street_ids;
    private List<String> outgoing_street_ids;

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

    public List<String> getIncoming_street_ids() {
        return incoming_street_ids;
    }

    public void setIncoming_street_ids(List<String> incoming_street_ids) {
        this.incoming_street_ids = incoming_street_ids;
    }

    public List<String> getOutgoing_street_ids() {
        return outgoing_street_ids;
    }

    public void setOutgoing_street_ids(List<String> outgoing_street_ids) {
        this.outgoing_street_ids = outgoing_street_ids;
    }
}
