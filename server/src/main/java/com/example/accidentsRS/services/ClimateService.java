package com.example.accidentsRS.services;

import com.example.accidentsRS.model.ClimateModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClimateService {
    void createClimateRecord(ClimateModel climateModel);
    List<ClimateModel> findAllMatchingFilters(List<FilterWrapperModel> accidentFilters);
}
