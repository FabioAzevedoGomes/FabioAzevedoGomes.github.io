package com.example.accidentsRS.model;

import java.util.List;

public class ExtendedIntersectionModel extends IntersectionModel {
    public static final String CONNECTED_STREET_IDS = "connectedStreets";
    private List<DirectionalStreetModel> connectedStreets;

    public List<DirectionalStreetModel> getConnectedStreets() {
        return connectedStreets;
    }

    public void setConnectedStreets(List<DirectionalStreetModel> connectedStreets) {
        this.connectedStreets = connectedStreets;
    }
}
