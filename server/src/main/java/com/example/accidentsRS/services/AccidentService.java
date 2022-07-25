package com.example.accidentsRS.services;

import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.model.Climate;
import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.filter.FilterWrapperModel;

import java.util.List;

public interface AccidentService {
    void createAccidentRecord(AccidentModel accidentModel);

    List<AccidentModel> findAllMatchingFilters(List<FilterWrapperModel> accidentFilters);

    void updateWithClimateData(Climate climate, Date dateTime);
}
