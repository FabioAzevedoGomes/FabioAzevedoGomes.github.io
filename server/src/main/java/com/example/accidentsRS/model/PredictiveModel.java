package com.example.accidentsRS.model;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "predictors")
public class PredictiveModel {

    public static final String VERSION = "version";
    private String version;

    public static final String MODEL = "model";
    private Binary model;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Binary getModel() {
        return model;
    }

    public void setModel(Binary model) {
        this.model = model;
    }
}
