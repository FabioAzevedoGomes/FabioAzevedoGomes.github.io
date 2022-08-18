package com.example.accidentsRS.model;

public abstract class GeoLocation extends PersistedModel {
    public static final String EXTERNAL_ID = "externalId";
    protected String externalId;
    public static final String LOCATION = "location";
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
