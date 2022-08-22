package com.example.accidentsRS.data;

import com.example.accidentsRS.model.*;

import java.util.List;

public class AccidentData {

    private String externalId;
    private Address address;
    private DateTimeModel date_time;
    private Fatality fatality;
    private List<String> involved_entities;
    private String type;
    private Climate climate;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public DateTimeModel getDate_time() {
        return date_time;
    }

    public void setDate_time(DateTimeModel date_time) {
        this.date_time = date_time;
    }

    public Fatality getFatality() {
        return fatality;
    }

    public void setFatality(Fatality fatality) {
        this.fatality = fatality;
    }

    public List<String> getInvolved_entities() {
        return involved_entities;
    }

    public void setInvolved_entities(List<String> involved_entities) {
        this.involved_entities = involved_entities;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Climate getClimate() {
        return climate;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }
}
