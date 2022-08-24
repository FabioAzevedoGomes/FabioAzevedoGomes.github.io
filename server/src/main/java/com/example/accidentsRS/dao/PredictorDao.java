package com.example.accidentsRS.dao;

import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.prediction.AggregatePredictorModel;
import com.example.accidentsRS.model.prediction.Predictor;
import com.example.accidentsRS.model.prediction.Region;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;

import java.io.IOException;
import java.util.List;

public interface PredictorDao {
    AggregatePredictorModel getPredictorByName(String name) throws PersistenceException, IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException;
    void savePredictor(Predictor predictor, List<Region> regionList);
    Region getRegionOfPointInModel(Location point, String modelName);
    void updateRegionRiskIndexes(List<Region> regionList);
}
