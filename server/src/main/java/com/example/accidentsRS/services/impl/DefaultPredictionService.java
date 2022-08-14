package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.dao.PredictiveModelDao;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.PredictiveModel;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.prediction.Predictor;
import com.example.accidentsRS.services.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@Component
public class DefaultPredictionService implements PredictionService {

    private static final Logger LOGGER = Logger.getLogger(DefaultPredictionService.class.getName());

    @Autowired
    Predictor defaultPredictor;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    PredictiveModelDao defaultPredictiveModelDao;

    @Override
    public void savePredictionModel(final PredictiveModel predictiveModel) throws PersistenceException {
        final PredictiveModel model = mongoOperations.findOne(
                Query.query(Criteria.where("version").is(predictiveModel.getVersion())),
                PredictiveModel.class
        );

        if (nonNull(model)) {
            throw new PersistenceException("Predictive model " + predictiveModel.getVersion() + " already exists!");
        }

        mongoOperations.save(predictiveModel);
    }

    @Override
    public PredictiveModel getPredictionModel(final String modelName) {
        return defaultPredictiveModelDao.getPredictionModel(modelName);
    }

    @Override
    public float predict(final Date date, final Location place) {
        try {
            return defaultPredictor.predictRiskForDateAndPlace(date, place);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error predicting risk for date " + date + " and place " + place, e);
            return 0.0f;
        }
    }
}
