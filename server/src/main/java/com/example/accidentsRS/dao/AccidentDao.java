package com.example.accidentsRS.dao;

import com.example.accidentsRS.model.AccidentModel;

import java.util.List;

public interface AccidentDao extends GenericDao<AccidentModel> {
    List<String> findDistinctStringField(String field);
    List<java.util.Date> findDistinctDates();
    float findMaxLat();
    float findMinLat();
    float findMaxLon();
    float findMinLon();
}
