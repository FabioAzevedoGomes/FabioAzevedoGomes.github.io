package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.services.ValueSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DefaultValueSearchService implements ValueSearchService {

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public List<String> getAllAccidentTypes() {
        return mongoOperations.findDistinct("type", AccidentModel.class, String.class);
    }

    @Override
    public List<String> getAllVehicleTypes() {
        return mongoOperations.findDistinct("involvedEntities", AccidentModel.class, String.class);
    }

    @Override
    public List<String> getAllRegions() {
        return mongoOperations.findDistinct("address.region", AccidentModel.class, String.class);
    }
}
