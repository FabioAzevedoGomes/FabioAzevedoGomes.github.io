package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.dao.PredictorDao;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.prediction.AggregatePredictorModel;
import com.example.accidentsRS.model.prediction.Predictor;
import com.example.accidentsRS.model.prediction.Region;
import com.example.accidentsRS.services.PredictionService;
import com.example.accidentsRS.services.factory.FeatureFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultPredictionService implements PredictionService {

    private static final Logger LOGGER = Logger.getLogger(DefaultPredictionService.class.getName());

    @Autowired
    FeatureFactory defaultFeatureFactory;

    @Autowired
    PredictorDao defaultPredictorDao;

    @Override
    public void savePredictor(final Predictor predictor, final List<Region> regionList) {
        defaultPredictorDao.savePredictor(predictor, regionList);
    }

    protected void persistPredictionResults(final List<Region> regionList) {

    }

    @Override
    public List<Region> forecastTodayUsing(final String modelName) {
        try {
            final AggregatePredictorModel model = defaultPredictorDao.getPredictorByName(modelName);
            LOGGER.log(Level.INFO, "Starting forecast for today");
            final Date now = new Date();
            for (Region region : model.getDomain()) {
                LOGGER.log(Level.INFO, "Predicting for region " + region.getRegionId());
                INDArray features = defaultFeatureFactory.getFeaturesForRegionAndDate(region, now);
                float prediction = model.predict(features, region);
                region.setRisk(prediction);
            }
            LOGGER.log(Level.INFO, "Done predicting for today");
            persistPredictionResults(model.getDomain());
            return model.getDomain();
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred trying to predict risk with " + modelName, e);
        }
        return new ArrayList<>();
    }

    @Override
    public float predictRiskAtPointUsing(final Location point, final String modelName) {
        try {
            final Region encompassingRegion = defaultPredictorDao.getRegionOfPointInModel(point, modelName);
            final AggregatePredictorModel model = defaultPredictorDao.getPredictorByName(modelName);
            final INDArray features = defaultFeatureFactory.getFeaturesForRegionAndDate(encompassingRegion, new Date());
            return model.predict(features, encompassingRegion);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred trying to predict risk with " + modelName, e);
        }
        return -1;
    }
}
