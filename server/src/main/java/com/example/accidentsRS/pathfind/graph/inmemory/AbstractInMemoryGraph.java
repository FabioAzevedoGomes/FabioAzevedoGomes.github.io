package com.example.accidentsRS.pathfind.graph.inmemory;

import com.example.accidentsRS.pathfind.graph.AbstractGraph;
import com.example.accidentsRS.pathfind.graph.Edge;
import com.example.accidentsRS.pathfind.graph.Node;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;

public abstract class AbstractInMemoryGraph extends AbstractGraph {

    protected static class SafeHashConnectionMap extends HashMap<String, Set<Pair<String, String>>> {
        public void update(final String key, final String edge, final String neighbor) {
            if (!this.containsKey(key)) {
                this.put(key, new HashSet<>());
            }
            this.get(key).add(Pair.of(edge, neighbor));
        }

        @Override
        public Set<Pair<String, String>> get(final Object key) {
            return nonNull(super.get(key)) ? super.get(key) : new HashSet<>();
        }
    }

    protected final HashMap<String, Node> loadedNodes;
    protected final HashMap<String, Edge> loadedEdges;
    protected final SafeHashConnectionMap loadedConnections;

    protected AbstractInMemoryGraph() {
        this.loadedNodes = new HashMap<>();
        this.loadedEdges = new HashMap<>();
        this.loadedConnections = new SafeHashConnectionMap();
    }
}
