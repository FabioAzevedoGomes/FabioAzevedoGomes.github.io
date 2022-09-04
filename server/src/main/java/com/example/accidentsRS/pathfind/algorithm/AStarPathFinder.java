package com.example.accidentsRS.pathfind.algorithm;

import com.example.accidentsRS.exceptions.NoSuchPathException;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.pathfind.graph.Edge;
import com.example.accidentsRS.pathfind.graph.Graph;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AStarPathFinder extends AbstractPathFinder {

    private static final Logger LOGGER = Logger.getLogger(AStarPathFinder.class.getName());

    private PriorityQueue<String> openSet;
    private HashMap<String, String> cameFrom;
    private SafeValueMap<String> valueOf;
    private SafeValueMap<String> heuristicValueOf;
    private Location destinationLocation;

    public AStarPathFinder(Graph graph) {
        super(graph);
    }

    public AStarPathFinder(final String modelName) {
        super();
        setModelName(modelName);
    }

    public AStarPathFinder() {

    }

    protected float getHeuristicScore(final String nodeId) {
        final Location nodeLocation = graph.getNode(nodeId).getData().getLocation();
        float lat1 = nodeLocation.getLatitude();
        float lat2 = destinationLocation.getLatitude();
        float lon1 = nodeLocation.getLongitude();
        float lon2 = destinationLocation.getLongitude();
        return (float) Math.sqrt((lat2 - lat1) * (lat2 - lat1) + (lon2 - lon1) * (lon2 - lon1));
    }

    @Override
    protected void initializeAlgorithm(final String startId, final String endId) {
        super.initializeAlgorithm(startId, endId);
        this.openSet = new PriorityQueue<>(new HeuristicNodeComparator());
        this.cameFrom = new HashMap<>();
        this.valueOf = new SafeValueMap<>();
        this.heuristicValueOf = new SafeValueMap<>();
        this.destinationLocation = graph.getNode(endId).getData().getLocation();

        this.openSet.add(startId);
        this.valueOf.put(startId, 0.0f);
        this.heuristicValueOf.put(startId, this.getHeuristicScore(startId));
    }

    @Override
    protected float getHeuristicValueOf(final String node) {
        return heuristicValueOf.get(node);
    }

    public List<Location> backtrack(final String from) {
        final Deque<Location> path = new LinkedList<>();
        path.addFirst(graph.getNode(from).getData().getLocation());
        String current = from;
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(graph.getNode(current).getData().getLocation());
        }
        return new ArrayList<>(path);
    }

    @Override
    public List<Location> getPathBetween(final String startNode, final String endNode) throws NoSuchPathException {
        if (isEmpty(startNode) || isEmpty(endNode)) {
            LOGGER.log(Level.SEVERE, "Empty start or end node!");
            throw new NoSuchPathException("Start or end node are empty!");
        }

        initializeAlgorithm(startNode, endNode);

        while (!CollectionUtils.isEmpty(openSet)) {
            final String current = openSet.poll();
            if (nonNull(current) && current.equals(endNode)) {
                return backtrack(current);
            }

            for (final Edge edge : graph.getNeighborEdges(current)) {
                String neighbor = edge.getData().getDestinationIntersectionId();
                float cost = edge.getWeight();
                float score = valueOf.get(current) + cost;
                if (score < valueOf.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    valueOf.put(neighbor, score);
                    heuristicValueOf.put(neighbor, score + getHeuristicScore(neighbor));
                    if (!openSet.contains(neighbor)) {
                        openSet.offer(neighbor);
                    }
                }
            }
        }
        LOGGER.log(Level.SEVERE, "Path not found!");
        throw new NoSuchPathException("Could not find a path");
    }
}
