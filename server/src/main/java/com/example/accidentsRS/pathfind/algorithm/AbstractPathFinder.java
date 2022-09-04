package com.example.accidentsRS.pathfind.algorithm;

import com.example.accidentsRS.exceptions.NoSuchPathException;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.pathfind.PathFinder;
import com.example.accidentsRS.pathfind.factory.GraphFactory;
import com.example.accidentsRS.pathfind.graph.Graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractPathFinder implements PathFinder {

    private GraphFactory defaultGraphFactory;
    private String modelName;

    protected static class SafeValueMap<S> extends HashMap<S, Float> {
        @Override
        public Float get(Object key) {
            if (this.containsKey(key)) {
                return super.get(key);
            } else {
                return Float.MAX_VALUE;
            }
        }
    }

    protected class HeuristicNodeComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            float o1Val = getHeuristicValueOf(o1);
            float o2Val = getHeuristicValueOf(o2);
            if (o1Val == o2Val) {
                return 0;
            } else {
                return (int) ((o1Val - o2Val) / Math.abs(o1Val - o2Val));
            }
        }
    }

    protected Graph graph;

    protected AbstractPathFinder(final Graph graph) {
        this.graph = graph;
    }

    protected AbstractPathFinder() {

    }

    protected void initializeAlgorithm(final String startId, final String endId) {
        this.graph = getDefaultGraphFactory().getChunkedGraph(this.modelName);
    }

    @Override
    public abstract List<Location> getPathBetween(String start, String end) throws NoSuchPathException;

    protected abstract float getHeuristicValueOf(String node);

    public GraphFactory getDefaultGraphFactory() {
        return defaultGraphFactory;
    }

    public void setDefaultGraphFactory(GraphFactory defaultGraphFactory) {
        this.defaultGraphFactory = defaultGraphFactory;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
