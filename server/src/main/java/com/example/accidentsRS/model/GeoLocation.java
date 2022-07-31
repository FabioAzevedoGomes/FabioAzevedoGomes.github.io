package com.example.accidentsRS.model;

public abstract class GeoLocation {
    protected String externalId;
    protected Location location;

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
}
