package com.example.accidentsRS.model.prediction;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "predictor")
public class Predictor {

    public static final String COLLECTION_NAME = "predictor";
    @Id
    public static final String NAME = "name";
    private String name;
    public static final String MODEL = "model";
    private Binary model;
    public static final String DOMAIN = "domain";
    private List<String> domain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Binary getModel() {
        return model;
    }

    public void setModel(Binary model) {
        this.model = model;
    }

    public List<String> getDomain() {
        return domain;
    }

    public void setDomain(List<String> domain) {
        this.domain = domain;
    }
}
