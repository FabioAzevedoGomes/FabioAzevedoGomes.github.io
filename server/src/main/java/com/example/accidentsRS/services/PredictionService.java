package com.example.accidentsRS.services;

import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.prediction.Predictor;
import com.example.accidentsRS.model.prediction.Region;

import java.util.List;

public interface PredictionService {
    void savePredictor(Predictor predictor, List<Region> regionList);
    List<Region> forecastTodayUsing(String modelName);
    float predictRiskAtPointUsing(Location point, String modelName);
    List<String> getPredictorNames();
}
