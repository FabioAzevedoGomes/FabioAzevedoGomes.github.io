package com.example.accidentsRS.facade;

import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;

import java.util.List;
import java.util.Map;

public interface ClimateFacade {
    void createClimateRecord(ClimateData inputClimateData) throws PersistenceException;

    List<ClimateData> findAllMatchingFilter(Map<String, Object> climateFilters) throws ValidationException;

    void updateAllMatchingFilter(Map<String, Object> climateFilters, Map<String, Object> newValue) throws ValidationException;

    void deleteAllMatchingFilter(Map<String, Object> climateFilters) throws ValidationException;
}
