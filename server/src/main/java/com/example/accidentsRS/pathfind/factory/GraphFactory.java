package com.example.accidentsRS.pathfind.factory;

import com.example.accidentsRS.pathfind.graph.Graph;

public interface GraphFactory {
    Graph getChunkedGraph(String modelName);
    Graph getCompleteGraph(String modelName);
    Graph getDatabaseGraph(String modelName);
}
