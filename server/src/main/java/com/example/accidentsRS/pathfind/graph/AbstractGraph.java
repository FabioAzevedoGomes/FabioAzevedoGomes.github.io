package com.example.accidentsRS.pathfind.graph;

import com.example.accidentsRS.dao.MapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public abstract class AbstractGraph implements Graph {

    @Autowired
    MapDao mapDao;

    protected MapDao getMapDao() {
        return mapDao;
    }

    public abstract Node getNode( String nodeId);
    public abstract Edge getEdge(String EdgeId);
}
