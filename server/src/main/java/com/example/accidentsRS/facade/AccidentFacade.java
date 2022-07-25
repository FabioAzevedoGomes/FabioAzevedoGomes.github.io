package com.example.accidentsRS.facade;

import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;

import java.util.List;
import java.util.Map;

public interface AccidentFacade {
    void createAccidentRecord(AccidentData inputClimateData) throws PersistenceException;

    List<AccidentData> findAllMatchingFilter(List<FilterWrapperData> accidentFilters) throws ValidationException;

    void updateAllMatchingFilter(Map<String, Object> climateFilters, Map<String, Object> newValue) throws ValidationException;

    void deleteAllMatchingFilter(Map<String, Object> climateFilters) throws ValidationException;
}
