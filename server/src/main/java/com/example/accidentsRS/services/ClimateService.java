package com.example.accidentsRS.services;

import com.example.accidentsRS.model.ClimateModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClimateService {
    void createClimateRecord(ClimateModel climateModel);
    List<ClimateModel> findAllMatchingFilters(List<FilterWrapperModel> climateFilters);
    void updateAllMatchingFilters(List<FilterWrapperModel> climateFilters, List<UpdateModel> updateValues);
    void deleteAllMatchingFilters(List<FilterWrapperModel> climateFilters);
}
