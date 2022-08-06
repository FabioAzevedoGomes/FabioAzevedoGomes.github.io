package com.example.accidentsRS.pathfind.graph;

import java.util.List;

public interface Graph {
    List<Edge> getNeighborEdges(String nodeId);
    List<Node> getNeighborNodes(String nodeId);
    Node getNode(String nodeId);
    Edge getEdge(String edgeId);
}
