package com.example.accidentsRS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "climate")
public class ClimateModel extends PersistedModel {

    @Id
    private String id;
    public static final String DATE_TIME = "dateTime";
    private Date dateTime;
    public static final String VISIBILITY = "visibility";
    private Float visibility;
    public static final String RELATIVE_HUMIDITY_PERCENTAGE = "relativeHumidityPercentage";
    private Float relativeHumidityPercentage;
    public static final String PRECIPITATION_MM = "precipitationMm";
    private Float precipitationMm;
    public static final String WIND_SPEED_MS = "windSpeedMs";
    private Float windSpeedMs;
    public static final String AIR_TEMP_CELSIUS = "airTempCelsius";
    private Float airTempCelsius;
    public static final String LOCATION = "location";
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
