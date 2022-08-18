package com.example.accidentsRS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "streets")
public class DirectionalStreetModel extends GeoLocation {

    public static final String COLLECTION_NAME = "streets";
    @Id
    private String id;
    public static final String DIRECTIONAL_ID = "directionalId";
    private String directionalId;
    public static final String NAME = "name";
    private String name;
    public static final String LENGTH = "length";
    private Float length;
    public static final String SOURCE_INTERSECTION_ID = "sourceIntersectionId";
    private String sourceIntersectionId;
    public static final String DESTINATION_INTERSECTION_ID = "destinationIntersectionId";
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

    public String getDirectionalId() {
        return directionalId;
    }

    public void setDirectionalId(String directionalId) {
        this.directionalId = directionalId;
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

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
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
