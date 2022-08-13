package com.example.accidentsRS.services;

import com.example.accidentsRS.model.Location;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ValueSearchService {
    List<String> getAllAccidentTypes();
    List<String> getAllVehicleTypes();
    List<String> getAllRegions();
    List<java.util.Date> getAllDates();
    Pair<Location, Location> getBoundingBox();
}
