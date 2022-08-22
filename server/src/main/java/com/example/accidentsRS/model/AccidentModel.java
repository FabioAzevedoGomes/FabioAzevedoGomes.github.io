package com.example.accidentsRS.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "accidents")
public class AccidentModel extends PersistedModel {

    @Id
    private String id;
    public static final String EXTERNAL_ID = "externalId";
    private String externalId;
    public static final String ADDRESS = "address";
    private Address address;
    public static final String DATE = "date";
    private DateTimeModel date;
    public static final String FATALITY = "fatality";
    private Fatality fatality;
    public static final String INVOLVED_ENTITIES = "involvedEntities";
    private List<String> involvedEntities;
    public static final String TYPE = "type";
    private String type;
    public static final String CLIMATE = "climate";
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

    public DateTimeModel getDate() {
        return date;
    }

    public void setDate(DateTimeModel dateTimeModel) {
        this.date = dateTimeModel;
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
