package com.example.accidentsRS.pathfind.graph;

import com.example.accidentsRS.dao.MapDao;
import com.example.accidentsRS.dao.PredictorDao;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.prediction.AggregatePredictorModel;

public abstract class AbstractGraph implements Graph {

    private static final Float FIRST_LEVEL_RISK_PENALIZATION = 1.0f;
    private static final Float SECOND_LEVEL_RISK_PENALIZATION = 2.0f;
    private static final Float THIRD_LEVEL_RISK_PENALIZATION = 10.0f;

    private MapDao mapDao;
    private PredictorDao predictorDao;
    private String modelName;
    private AggregatePredictorModel predictiveModel;

    protected MapDao getMapDao() {
        return mapDao;
    }

    protected PredictorDao getPredictorDao() {
        return predictorDao;
    }

    public void setMapDao(MapDao mapDao) {
        this.mapDao = mapDao;
    }

    public void setPredictorDao(PredictorDao predictorDao) {
        this.predictorDao = predictorDao;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
        try {
            this.setPredictiveModel(predictorDao.getPredictorByName(modelName));
        } catch (Exception exception) {
            // TODO
        }
    }

    protected float getEdgeWeight(final DirectionalStreetModel street) {
        float normalizedRisk = getPredictiveModel().normalizeResult(street.getRisk());
        float penalizationFactor = 0.0f;
        if (normalizedRisk < 0.3f) {
            penalizationFactor = FIRST_LEVEL_RISK_PENALIZATION;
        } else if (normalizedRisk < 0.6f) {
            penalizationFactor = SECOND_LEVEL_RISK_PENALIZATION;
        } else {
            penalizationFactor = THIRD_LEVEL_RISK_PENALIZATION;
        }
        penalizationFactor = 10.0f;
        return street.getLength() * (1.0f + (normalizedRisk * penalizationFactor));
    }


    public AggregatePredictorModel getPredictiveModel() {
        return predictiveModel;
    }

    public void setPredictiveModel(AggregatePredictorModel predictiveModel) {
        this.predictiveModel = predictiveModel;
    }

    public abstract Node getNode(String nodeId);

    public abstract Edge getEdge(String edgeId);
}
