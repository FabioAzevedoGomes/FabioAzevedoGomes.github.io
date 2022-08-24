package com.example.accidentsRS.model;

import org.springframework.data.geo.Point;

public class Location {

    public static final String LONGITUDE = "longitude";
    private Float longitude;
    public static final String LATITUDE = "latitude";
    private Float latitude;

    public Location() {
    }

    public Location(Float longitude, Float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Point toPoint() {
        return new Point(this.longitude, this.latitude);
    }
}
