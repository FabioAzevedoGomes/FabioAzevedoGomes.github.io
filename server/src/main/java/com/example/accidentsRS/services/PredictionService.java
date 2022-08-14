package com.example.accidentsRS.services;

import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.PredictiveModel;

public interface PredictionService {
    void savePredictionModel(PredictiveModel predictiveModel) throws PersistenceException;
    PredictiveModel getPredictionModel(String modelName);
    float predict(java.util.Date date, Location place);
}
