package com.example.accidentsRS.dao;

import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.PredictiveModel;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;

import java.io.IOException;

public interface PredictiveModelDao {
    PredictiveModel getPredictionModel(String modelName);
    ComputationGraph getNetworkByName(String modelName) throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException, PersistenceException;
}
