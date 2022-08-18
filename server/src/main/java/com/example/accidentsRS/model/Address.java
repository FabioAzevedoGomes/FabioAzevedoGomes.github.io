package com.example.accidentsRS.model;

public class Address {

    public static final String STREET1 = "street1";
    private String street1;
    public static final String STREET2 = "street2";
    private String street2;
    public static final String REGION = "region";
    private String region;
    public static final String LOCATION = "location";
    private Location location;

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
