package com.example.accidentsRS.pathfind.graph;

import com.example.accidentsRS.dao.MapDao;

public abstract class AbstractGraph implements Graph {

    private MapDao mapDao;

    protected MapDao getMapDao() {
        return mapDao;
    }

    public void setMapDao(MapDao mapDao) {
        this.mapDao = mapDao;
    }

    public abstract Node getNode(String nodeId);
    public abstract Edge getEdge(String edgeId);
}
