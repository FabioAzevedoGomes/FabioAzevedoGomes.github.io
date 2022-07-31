package com.example.accidentsRS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "intersections")
public class IntersectionModel extends GeoLocation {

    @Id
    private String id;
    private List<String> connectedStreetIds;
    private List<String> incomingStreetIds;
    private List<String> outgoingStreetIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<String> getConnectedStreetIds() {
        return connectedStreetIds;
    }

    public void setConnectedStreetIds(List<String> connectedStreetIds) {
        this.connectedStreetIds = connectedStreetIds;
    }

    public List<String> getIncomingStreetIds() {
        return incomingStreetIds;
    }

    public void setIncomingStreetIds(List<String> incomingStreetIds) {
        this.incomingStreetIds = incomingStreetIds;
    }

    public List<String> getOutgoingStreetIds() {
        return outgoingStreetIds;
    }

    public void setOutgoingStreetIds(List<String> outgoingStreetIds) {
        this.outgoingStreetIds = outgoingStreetIds;
    }
}
