package com.example.accidentsRS.model;

import org.springframework.data.geo.Point;

public class Location {

    private Float longitude;
    private Float latitude;

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
