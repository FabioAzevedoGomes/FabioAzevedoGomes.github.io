package com.example.accidentsRS.data;

import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.Location;

public class ClimateData {

    private Date date_time;
    private Float visibility;
    private Float relative_humidity_percentage;
    private Float precipitation_mm;
    private Float wind_speed_ms;
    private Float air_temp_celcius;
    private Location location;

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public Float getVisibility() {
        return visibility;
    }

    public void setVisibility(Float visibility) {
        this.visibility = visibility;
    }

    public Float getRelative_humidity_percentage() {
        return relative_humidity_percentage;
    }

    public void setRelative_humidity_percentage(Float relative_humidity_percentage) {
        this.relative_humidity_percentage = relative_humidity_percentage;
    }

    public Float getPrecipitation_mm() {
        return precipitation_mm;
    }

    public void setPrecipitation_mm(Float precipitation_mm) {
        this.precipitation_mm = precipitation_mm;
    }

    public Float getWind_speed_ms() {
        return wind_speed_ms;
    }

    public void setWind_speed_ms(Float wind_speed_ms) {
        this.wind_speed_ms = wind_speed_ms;
    }

    public Float getAir_temp_celcius() {
        return air_temp_celcius;
    }

    public void setAir_temp_celcius(Float air_temp_celcius) {
        this.air_temp_celcius = air_temp_celcius;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
