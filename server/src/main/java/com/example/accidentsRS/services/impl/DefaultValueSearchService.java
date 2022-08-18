package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.dao.AccidentDao;
import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.model.Address;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.services.ValueSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultValueSearchService implements ValueSearchService {

    @Autowired
    AccidentDao defaultAccidentDao;

    @Override
    public List<String> getAllAccidentTypes() {
        return defaultAccidentDao.findDistinctStringField(AccidentModel.TYPE);
    }

    @Override
    public List<String> getAllVehicleTypes() {
        return defaultAccidentDao.findDistinctStringField(AccidentModel.INVOLVED_ENTITIES);
    }

    @Override
    public List<String> getAllRegions() {
        return defaultAccidentDao.findDistinctStringField(AccidentModel.ADDRESS + "." + Address.REGION);
    }

    @Override
    public List<java.util.Date> getAllDates() {
        return defaultAccidentDao.findDistinctDates();
    }

    @Override
    public Pair<Location, Location> getBoundingBox() {
        return Pair.of(
                new Location(
                        defaultAccidentDao.findMinLon(),
                        defaultAccidentDao.findMinLat()
                ),
                new Location(
                        defaultAccidentDao.findMaxLon(),
                        defaultAccidentDao.findMaxLat()
                )
        );
    }
}
