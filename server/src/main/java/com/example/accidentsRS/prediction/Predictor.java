package com.example.accidentsRS.prediction;

import com.example.accidentsRS.model.Location;

public interface Predictor {
    float predictRiskForDateAndPlace(java.util.Date date, Location place) throws Exception;
}
