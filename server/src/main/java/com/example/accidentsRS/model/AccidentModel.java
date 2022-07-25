package com.example.accidentsRS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "accidents")
public class AccidentModel {

    @Id
    private String id;
    private String externalId;
    private Address address;
    private Date date;
    private Fatality fatality;
    private List<String> involvedEntities;
    private String type;
    private Climate climate;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Fatality getFatality() {
        return fatality;
    }

    public void setFatality(Fatality fatality) {
        this.fatality = fatality;
    }

    public List<String> getInvolvedEntities() {
        return involvedEntities;
    }

    public void setInvolvedEntities(List<String> involvedEntities) {
        this.involvedEntities = involvedEntities;
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
