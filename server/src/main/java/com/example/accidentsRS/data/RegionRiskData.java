package com.example.accidentsRS.data;

import com.example.accidentsRS.model.Location;

public class RegionRiskData {
    private Location location;
    private float riskIndex;

    public RegionRiskData() {
    }

    public RegionRiskData(Location location, float riskIndex) {
        this.location = location;
        this.riskIndex = riskIndex;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getRiskIndex() {
        return riskIndex;
    }

    public void setRiskIndex(float riskIndex) {
        this.riskIndex = riskIndex;
    }
}
