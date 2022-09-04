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

    private static final float EXPANSION_RADIUS_KILOMETERS = 10.0f;

    protected float getRiskAssociatedWithStreet(final DirectionalStreetModel directionalStreetModel) {
        final Region region = getPredictorDao().getRegionOfPointInModel(
                directionalStreetModel.getLocation(),
                getModelName()
        );
        if (nonNull(region) && region.getRisk() > 0) {
            return region.getRisk();
        } else {
            return 0.0f;
        }
    }

    protected void loadRegionAroundNode(final String nodeId) {
        populateExpansionResults(super.getMapDao().getCircleAroundIntersection(nodeId, EXPANSION_RADIUS_KILOMETERS));
    }

    protected void loadRegionAroundEdge(final String edgeId) {
        populateExpansionResults(super.getMapDao().getCircleAroundStreet(edgeId, EXPANSION_RADIUS_KILOMETERS));
    }

    protected void populateExpansionResults(Pair<List<IntersectionModel>, List<DirectionalStreetModel>> expansionResults) {
        LOGGER.log(Level.INFO, "Populating expansion results");
        expansionResults.getFirst().forEach(intersection ->
                loadedNodes.put(intersection.getExternalId(), new Node(intersection))
        );
        expansionResults.getSecond().forEach(street -> {
            loadedEdges.put(street.getDirectionalId(), new Edge(
                    street,
                    getRiskAssociatedWithStreet(street))
            );
            loadedConnections.update(
                    street.getSourceIntersectionId(),
                    street.getDirectionalId(),
                    street.getDirectionalId()
            );
        });
        LOGGER.log(Level.INFO, "done");
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
