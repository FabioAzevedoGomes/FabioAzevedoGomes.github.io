package com.example.accidentsRS.model.prediction;

import com.example.accidentsRS.model.Location;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "region")
public class Region {

    public static final String COLLECTION_NAME = "region";
    public static final String REGION_ID = "_id";
    @Id
    private String regionId;
    public static final String PREDICTOR = "predictor";
    private String predictor;
    public static final String CENTER = "center";
    private Location center;
    public static final String NORMALIZED_CENTER = "normalizedCenter";
    private Location normalizedCenter;
    public static final String RISK = "risk";
    private float risk;
    public static final String BOUNDS = "bounds";
    private Bounds bounds;

    public Region() {
    }

    public Region(String regionId, String predictor, Location center, float risk, Bounds bounds) {
        this.regionId = regionId;
        this.predictor = predictor;
        this.center = center;
        this.risk = risk;
        this.bounds = bounds;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getPredictor() {
        return predictor;
    }

    public void setPredictor(String predictor) {
        this.predictor = predictor;
    }

    public Location getCenter() {
        return center;
    }

    public Location getNormalizedCenter() {
        return normalizedCenter;
    }

    public void setNormalizedCenter(Location normalizedCenter) {
        this.normalizedCenter = normalizedCenter;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public float getRisk() {
        return risk;
    }

    public void setRisk(float risk) {
        this.risk = risk;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }
}
