package com.example.accidentsRS.pathfind.graph;

import com.example.accidentsRS.dao.MapDao;
import com.example.accidentsRS.dao.PredictorDao;

public abstract class AbstractGraph implements Graph {

    private MapDao mapDao;
    private PredictorDao predictorDao;
    private String modelName;

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

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public abstract Node getNode(String nodeId);

    public abstract Edge getEdge(String edgeId);
}
