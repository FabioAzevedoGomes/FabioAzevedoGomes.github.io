package com.example.accidentsRS.dao;

import com.example.accidentsRS.model.ClimateModel;

import java.util.Date;
import java.util.List;

public interface ClimateDao extends GenericDao<ClimateModel> {
    List<ClimateModel> getClimateOnDate(Date date);
}