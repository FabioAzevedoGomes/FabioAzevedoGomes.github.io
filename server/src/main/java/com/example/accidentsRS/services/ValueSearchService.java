package com.example.accidentsRS.services;

import java.util.List;

public interface ValueSearchService {
    List<String> getAllAccidentTypes();

    List<String> getAllVehicleTypes();

    List<String> getAllRegions();
}
