package com.example.accidentsRS.model;

public class Climate {

    public static final String VISIBILITY = "visibility";
    private Float visibility;
    public static final String RELATIVE_HUMIDITY_PERCENTAGE = "relative_humidity_percentage";
    private Float relative_humidity_percentage;
    public static final String PRECIPITATION_MM = "precipitation_mm";
    private Float precipitation_mm;
    public static final String WIND_SPEED_MS = "wind_speed_ms";
    private Float wind_speed_ms;
    public static final String AIR_TEMP_CELSIUS = "air_temp_celsius";
    private Float air_temp_celsius;

    public Climate(Float visibility, Float relative_humidity_percentage, Float precipitation_mm, Float wind_speed_ms, Float air_temp_celsius) {
        this.visibility = visibility;
        this.relative_humidity_percentage = relative_humidity_percentage;
        this.precipitation_mm = precipitation_mm;
        this.wind_speed_ms = wind_speed_ms;
        this.air_temp_celsius = air_temp_celsius;
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

    public Float getAir_temp_celsius() {
        return air_temp_celsius;
    }

    public void setAir_temp_celsius(Float air_temp_celsius) {
        this.air_temp_celsius = air_temp_celsius;
    }
}
