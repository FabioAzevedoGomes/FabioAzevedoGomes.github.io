package com.example.accidentsRS.services;

import com.example.accidentsRS.model.ClimateModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ClimateService {
    void createClimateRecord(ClimateModel climateModel);
    List<ClimateModel> findAllMatchingFilters(Map<String, Object> accidentFilters);
}
