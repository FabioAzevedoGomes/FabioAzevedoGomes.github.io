package com.example.accidentsRS.facade;

import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.endpoints.data.UpdateWrapper;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;

import java.util.List;

public interface ClimateFacade {
    void createClimateRecord(ClimateData inputClimateData) throws PersistenceException;
    List<ClimateData> findAllMatchingFilter(List<FilterWrapperData> climateFilters) throws ValidationException;
    void updateAllMatchingFilter(UpdateWrapper updateWrapper) throws ValidationException;
    void deleteAllMatchingFilter(List<FilterWrapperData> accidentFilters) throws ValidationException;
}
