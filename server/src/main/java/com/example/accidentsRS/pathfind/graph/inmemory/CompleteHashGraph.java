package com.example.accidentsRS.pathfind.graph.inmemory;

import com.example.accidentsRS.pathfind.graph.Edge;
import com.example.accidentsRS.pathfind.graph.Node;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CompleteHashGraph extends AbstractInMemoryGraph {

    private static final Logger LOGGER = Logger.getLogger(CompleteHashGraph.class.getName());

    private boolean isInitialized;

    protected void initializeGraph() {
        super.getMapDao().getAllIntersections().forEach(intersection -> {
            this.loadedNodes.put(intersection.getExternalId(), new Node(intersection));
        });
        super.getMapDao().getAllStreetsWithRisk(this.getModelName()).forEach(street -> {
            this.loadedEdges.put(street.getDirectionalId(), new Edge(street, getEdgeWeight(street)));
            this.loadedConnections.update(
                    street.getSourceIntersectionId(),
                    street.getDirectionalId(),
                    street.getDestinationIntersectionId()
            );
        });
        this.isInitialized = true;
    }

    protected void checkInitialized() {
        if (!isInitialized) {
            initializeGraph();
        }
    }

    @Override
    public List<Edge> getNeighborEdges(String nodeId) {
        checkInitialized();
        return loadedConnections.get(nodeId).stream()
                .map(edgeNodePair -> loadedEdges.get(edgeNodePair.getFirst()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Node> getNeighborNodes(String nodeId) {
        checkInitialized();
        return loadedConnections.get(nodeId).stream()
                .map(edgeNodePair -> loadedNodes.get(edgeNodePair.getSecond()))
                .collect(Collectors.toList());
    }

    @Override
    public Node getNode(final String nodeId) {
        checkInitialized();
        return loadedNodes.get(nodeId);
    }

    @Override
    public Edge getEdge(final String edgeId) {
        checkInitialized();
        return loadedEdges.get(edgeId);
    }
}
