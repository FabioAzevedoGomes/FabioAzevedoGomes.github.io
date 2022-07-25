package com.example.accidentsRS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "climate")
public class ClimateModel {

    @Id
    private String id;
    private Date dateTime;
    private Float visibility;
    private Float relativeHumidityPercentage;
    private Float precipitationMm;
    private Float windSpeedMs;
    private Float airTempCelsius;
    private Location location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Float getVisibility() {
        return visibility;
    }

    public void setVisibility(Float visibility) {
        this.visibility = visibility;
    }

    public Float getRelativeHumidityPercentage() {
        return relativeHumidityPercentage;
    }

    public void setRelativeHumidityPercentage(Float relativeHumidityPercentage) {
        this.relativeHumidityPercentage = relativeHumidityPercentage;
    }

    public Float getPrecipitationMm() {
        return precipitationMm;
    }

    public void setPrecipitationMm(Float precipitationMm) {
        this.precipitationMm = precipitationMm;
    }

    public Float getWindSpeedMs() {
        return windSpeedMs;
    }

    public void setWindSpeedMs(Float windSpeedMs) {
        this.windSpeedMs = windSpeedMs;
    }

    public Float getAirTempCelsius() {
        return airTempCelsius;
    }

    public void setAirTempCelsius(Float airTempCelsius) {
        this.airTempCelsius = airTempCelsius;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
