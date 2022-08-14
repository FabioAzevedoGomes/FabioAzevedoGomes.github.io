package com.example.accidentsRS.prediction;

import com.example.accidentsRS.dao.PredictiveModelDao;
import com.example.accidentsRS.model.Location;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
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

    @Autowired
    PredictiveModelDao defaultPredictiveModelDao;

    protected INDArray getInputFeatures() {
        final INDArray firstInputFeatures = Nd4j.zeros(PERIOD_SIZE_DAYS, NUM_FIRST_FEATURES);
        final INDArray secondInputFeatures = Nd4j.zeros(NUM_SECOND_FEATURES); // TODO Multiple inputs?
        return firstInputFeatures;
    }

    @Override
    public float predictRiskForDateAndPlace(final Date date, final Location place) throws Exception {
        final MultiLayerNetwork model = defaultPredictiveModelDao.getNetworkByName(getModelName());
        final INDArray inputData = getInputFeatures();
        return model.output(inputData).getFloat(0);
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
