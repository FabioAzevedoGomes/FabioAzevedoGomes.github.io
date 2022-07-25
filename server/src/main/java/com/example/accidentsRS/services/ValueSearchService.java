package com.example.accidentsRS.services;

import com.example.accidentsRS.model.AccidentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ValueSearchService {

    @Autowired
    MongoOperations mongoOperations;

    public List<String> getAllAccidentTypes() {
        return mongoOperations.findDistinct("type", AccidentModel.class, String.class);
    }

    public List<String> getAllVehicleTypes() {
        return mongoOperations.findDistinct("involvedEntities", AccidentModel.class, String.class);
    }

    public List<String> getAllRegions() {
        return mongoOperations.findDistinct("address.region", AccidentModel.class, String.class);
    }
}
