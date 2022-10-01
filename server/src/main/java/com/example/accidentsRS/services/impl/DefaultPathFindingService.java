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
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultPathFindingService implements PathfindingService {

    private static final Logger LOGGER = Logger.getLogger(DefaultPathFindingService.class.getName());

    @Autowired
    PathfinderFactory defaultPathfinderFactory;

    @Override
    public List<Location> findPath(final PathSuggestionParameterWrapper pathSuggestionParameterWrapper,
                                   final String modelName) {
        final PathFinder pathFinderAlgorithm = defaultPathfinderFactory.getAStarPathfinder(modelName);
        List<Location> path;
        long startTime = System.nanoTime();
        try {
            path = pathFinderAlgorithm.getPathBetween(
                    pathSuggestionParameterWrapper.getStartPointId(),
                    pathSuggestionParameterWrapper.getEndPointId()
            );
        } catch (final NoSuchPathException noSuchPathException) {
            path = new ArrayList<>();
        }
        long stopTime = System.nanoTime();
        LOGGER.log(Level.INFO, "Path finding took " + (stopTime - startTime) + " nano seconds");
        return path;
    }
}
