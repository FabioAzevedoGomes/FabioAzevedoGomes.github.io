package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.data.PathSuggestionParameterWrapper;
import com.example.accidentsRS.exceptions.NoSuchPathException;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.pathfind.PathFinder;
import com.example.accidentsRS.pathfind.algorithm.AStarPathFinder;
import com.example.accidentsRS.pathfind.factory.GraphFactory;
import com.example.accidentsRS.pathfind.factory.PathfinderFactory;
import com.example.accidentsRS.services.PathfindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultPathFindingService implements PathfindingService {

    @Autowired
    PathfinderFactory defaultPathfinderFactory;

    @Override
    public List<Location> findPath(final PathSuggestionParameterWrapper pathSuggestionParameterWrapper,
                                   final String modelName) {
        final PathFinder pathFinderAlgorithm = defaultPathfinderFactory.getAStarPathfinder(modelName);
        List<Location> path;
        try {
            path = pathFinderAlgorithm.getPathBetween(
                    pathSuggestionParameterWrapper.getStartPointId(),
                    pathSuggestionParameterWrapper.getEndPointId()
            );
        } catch (final NoSuchPathException noSuchPathException) {
            path = new ArrayList<>();
        }
        return path;
    }
}
