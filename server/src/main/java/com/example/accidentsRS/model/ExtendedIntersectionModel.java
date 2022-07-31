package com.example.accidentsRS.model;

import java.util.List;

public class ExtendedIntersectionModel extends IntersectionModel {
    private List<DirectionalStreetModel> connectedStreets;

    public List<DirectionalStreetModel> getConnectedStreets() {
        return connectedStreets;
    }

    public void setConnectedStreets(List<DirectionalStreetModel> connectedStreets) {
        this.connectedStreets = connectedStreets;
    }
}
