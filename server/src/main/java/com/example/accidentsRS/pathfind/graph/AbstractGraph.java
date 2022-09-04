package com.example.accidentsRS.pathfind.graph;

import com.example.accidentsRS.dao.MapDao;
import com.example.accidentsRS.dao.PredictorDao;
import com.example.accidentsRS.model.prediction.AggregatePredictorModel;

public abstract class AbstractGraph implements Graph {

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

    public AggregatePredictorModel getPredictiveModel() {
        return predictiveModel;
    }

    public void setPredictiveModel(AggregatePredictorModel predictiveModel) {
        this.predictiveModel = predictiveModel;
    }

    public abstract Node getNode(String nodeId);

    public abstract Edge getEdge(String edgeId);
}
