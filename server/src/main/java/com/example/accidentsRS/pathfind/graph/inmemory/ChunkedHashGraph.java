package com.example.accidentsRS.pathfind.graph.inmemory;

import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.pathfind.graph.Edge;
import com.example.accidentsRS.pathfind.graph.Node;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class ChunkedHashGraph extends AbstractInMemoryGraph {

    private static final float EXPANSION_RADIUS = 0.5f;

    protected void loadRegionAroundNode(final String nodeId) {
        populateExpansionResults(super.getMapDao().getCircleAroundIntersection(nodeId, EXPANSION_RADIUS));
    }

    protected void loadRegionAroundEdge(final String edgeId) {
        populateExpansionResults(super.getMapDao().getCircleAroundStreet(edgeId, EXPANSION_RADIUS));
    }

    protected void populateExpansionResults(Pair<List<IntersectionModel>, List<DirectionalStreetModel>> expansionResults) {
        expansionResults.getFirst().forEach(intersection -> {
            loadedNodes.put(intersection.getExternalId(), new Node(intersection));
        });
        expansionResults.getSecond().forEach(street -> {
            loadedEdges.put(street.getDirectionalId(), new Edge(street));
            loadedConnections.update(
                    street.getSourceIntersectionId(),
                    street.getDirectionalId(),
                    street.getDirectionalId()
            );
        });
    }

    @Override
    public List<Edge> getNeighborEdges(final String nodeId) {
        getNode(nodeId); // Ensuring we have the node and neighbors loaded

        return loadedConnections.get(nodeId).stream()
                .map(edgeNeighborPair -> loadedEdges.get(edgeNeighborPair.getFirst()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Node> getNeighborNodes(final String nodeId) {
        getNode(nodeId); // Ensuring we have the node and neighbors loaded

        return loadedConnections.get(nodeId).stream()
                .map(edgeNeighborPair -> loadedNodes.get(edgeNeighborPair.getSecond()))
                .collect(Collectors.toList());
    }

    @Override
    public Node getNode(final String nodeId) {
        if (!loadedNodes.containsKey(nodeId) || !loadedConnections.containsKey(nodeId)) {
            loadRegionAroundNode(nodeId);
        }
        return loadedNodes.get(nodeId);
    }

    @Override
    public Edge getEdge(final String edgeId) {
        if (!loadedEdges.containsKey(edgeId)) {
            loadRegionAroundEdge(edgeId);
        }
        return loadedEdges.get(edgeId);
    }
}
