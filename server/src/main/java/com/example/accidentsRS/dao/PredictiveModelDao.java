package com.example.accidentsRS.dao;

import com.example.accidentsRS.model.PredictiveModel;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.IOException;

public interface PredictiveModelDao {
    PredictiveModel getPredictionModel(String modelName);
    MultiLayerNetwork getNetworkByName(String modelName) throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException;
}
