package com.example.accidentsRS.model;

public class Fatality {

    private int deaths;
    private int light_injuries;
    private int serious_injuries;

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getLight_injuries() {
        return light_injuries;
    }

    public void setLight_injuries(int light_injuries) {
        this.light_injuries = light_injuries;
    }

    public int getSerious_injuries() {
        return serious_injuries;
    }

    public void setSerious_injuries(int serious_injuries) {
        this.serious_injuries = serious_injuries;
    }
}
