package com.example.accidentsRS.prediction.impl;

import com.example.accidentsRS.dao.AccidentDao;
import com.example.accidentsRS.dao.MapDao;
import com.example.accidentsRS.dao.PredictiveModelDao;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.prediction.Predictor;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DefaultPredictor implements Predictor {

    @Value("${predictive.model.name}")
    private String modelName;

    private static final int NUM_FIRST_FEATURES = 9;
    private static final int NUM_SECOND_FEATURES = 2;
    private static final int PERIOD_SIZE_DAYS = 7;
    private static final int BATCH_SIZE = 1;

    @Autowired
    PredictiveModelDao defaultPredictiveModelDao;

    @Autowired
    AccidentDao defaultAccidentDao;

    @Autowired
    MapDao defaultMapDao;

    protected INDArray[] getInputFeatures(final Date date, final Location place) {
        final INDArray firstInputFeatures = Nd4j.zeros(BATCH_SIZE, NUM_FIRST_FEATURES, PERIOD_SIZE_DAYS);
        final INDArray secondInputFeatures = Nd4j.zeros(BATCH_SIZE, NUM_SECOND_FEATURES);
        return new INDArray[]{firstInputFeatures, secondInputFeatures};
    }

    @Override
    public float predictRiskForDateAndPlace(final Date date, final Location place) throws Exception {
        final ComputationGraph model = defaultPredictiveModelDao.getNetworkByName(getModelName());
        final INDArray[] inputData = getInputFeatures(date, place);
        return model.output(inputData)[0].getFloat(0);
    }

    @Override
    public void forecastForToday() {

    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
