package com.example.accidentsRS.pathfind.graph.inmemory;

import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.model.prediction.Region;
import com.example.accidentsRS.pathfind.graph.Edge;
import com.example.accidentsRS.pathfind.graph.Node;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ChunkedHashGraph extends AbstractInMemoryGraph {

    private static final Logger LOGGER = Logger.getLogger(ChunkedHashGraph.class.getName());
    private static final Float PENALIZATION_BY_RISK = 10.0f;

    private static final Float FIRST_LEVEL_RISK_PENALIZATION = 1.0f;
    private static final Float SECOND_LEVEL_RISK_PENALIZATION = 2.0f;
    private static final Float THIRD_LEVEL_RISK_PENALIZATION = 10.0f;

    protected void loadRegionAroundNode(final String nodeId) {
        populateExpansionResults(super.getMapDao().getRegionAroundIntersectionWithRisk(nodeId, this.getModelName()));
    }

    protected void loadRegionAroundEdge(final String edgeId) {
        populateExpansionResults(super.getMapDao().getRegionAroundStreetWithRisk(edgeId, this.getModelName()));
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
        return street.getLength() * (1.0f + (normalizedRisk * penalizationFactor));
    }

    protected void populateExpansionResults(Pair<List<IntersectionModel>, List<DirectionalStreetModel>> expansionResults) {
        expansionResults.getFirst().forEach(intersection ->
                loadedNodes.put(intersection.getExternalId(), new Node(intersection))
        );
        expansionResults.getSecond().forEach(street -> {
            loadedEdges.put(street.getDirectionalId(), new Edge(street, getEdgeWeight(street)));
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
        if (!loadedNodes.containsKey(nodeId)) {
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
