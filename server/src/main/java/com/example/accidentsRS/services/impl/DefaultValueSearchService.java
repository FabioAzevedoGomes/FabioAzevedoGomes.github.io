package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.services.ValueSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public List<java.util.Date> getAllDates() {
        return mongoOperations.findDistinct("date.date", AccidentModel.class, java.util.Date.class);
    }

    @Override
    public Pair<Location, Location> getBoundingBox() {
        float maxLat = mongoOperations.findOne(
                new Query().limit(1)
                        .with(Sort.by("address.location.latitude").descending()),
                AccidentModel.class
        ).getAddress().getLocation().getLatitude();

        float minLat = mongoOperations.findOne(
                new Query().limit(1)
                        .with(Sort.by("address.location.latitude").ascending()),
                AccidentModel.class
        ).getAddress().getLocation().getLatitude();

        float maxLon = mongoOperations.findOne(
                new Query().limit(1)
                        .with(Sort.by("address.location.longitude").descending()),
                AccidentModel.class
        ).getAddress().getLocation().getLongitude();

        float minLon = mongoOperations.findOne(
                new Query().limit(1)
                        .with(Sort.by("address.location.longitude").ascending()),
                AccidentModel.class
        ).getAddress().getLocation().getLongitude();

        return Pair.of(new Location(minLon, minLat), new Location(maxLon, maxLat));
    }
}
