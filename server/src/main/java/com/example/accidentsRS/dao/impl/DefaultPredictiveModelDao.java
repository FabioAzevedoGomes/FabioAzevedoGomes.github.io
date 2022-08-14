package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.PredictiveModelDao;
import com.example.accidentsRS.model.PredictiveModel;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DefaultPredictiveModelDao implements PredictiveModelDao {

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public PredictiveModel getPredictionModel(final String modelName) {
        return mongoOperations.findOne(
                Query.query(Criteria.where(PredictiveModel.VERSION).is(modelName)),
                PredictiveModel.class
        );
    }

    @Override
    public MultiLayerNetwork getNetworkByName(final String modelName) throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        final PredictiveModel predictiveModel = getPredictionModel(modelName);
        final InputStream modelByteStream = new ByteArrayInputStream(predictiveModel.getModel().getData());
        return KerasModelImport.importKerasSequentialModelAndWeights(modelByteStream);
    }
}
