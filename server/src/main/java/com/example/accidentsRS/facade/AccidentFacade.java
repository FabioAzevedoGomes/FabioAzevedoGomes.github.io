package com.example.accidentsRS.facade;

import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.endpoints.data.UpdateWrapper;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.model.Location;
import org.springframework.data.util.Pair;

import java.util.List;

public interface AccidentFacade {
    void createAccidentRecord(AccidentData inputClimateData) throws PersistenceException;
    List<AccidentData> findAllMatchingFilter(List<FilterWrapperData> accidentFilters) throws ValidationException;
    void updateAllMatchingFilter(UpdateWrapper updateWrapper) throws ValidationException;
    void deleteAllMatchingFilter(List<FilterWrapperData> accidentFilters) throws ValidationException;
    List<String> getAllAccidentTypes();
    List<String> getAllVehicleTypes();
    List<String> getAllRegions();
    List<java.util.Date> getAllDates();
    Pair<Location, Location> getBoundingBox();
}
