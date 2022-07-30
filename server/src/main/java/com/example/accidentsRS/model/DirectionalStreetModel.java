package com.example.accidentsRS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "streets")
public class DirectionalStreetModel {

    @Id
    private String id;
    private String externalId;
    private String name;
    private Location location;
    private String sourceIntersectionId;
    private String destinationIntersectionId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSourceIntersectionId() {
        return sourceIntersectionId;
    }

    public void setSourceIntersectionId(String sourceIntersectionId) {
        this.sourceIntersectionId = sourceIntersectionId;
    }

    public String getDestinationIntersectionId() {
        return destinationIntersectionId;
    }

    public void setDestinationIntersectionId(String destinationIntersectionId) {
        this.destinationIntersectionId = destinationIntersectionId;
    }
}
