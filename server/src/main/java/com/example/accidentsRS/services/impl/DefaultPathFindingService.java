package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.data.PathSuggestionParameterWrapper;
import com.example.accidentsRS.exceptions.NoSuchPathException;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.pathfind.PathFinder;
import com.example.accidentsRS.pathfind.algorithm.AStarPathFinder;
import com.example.accidentsRS.services.PathfindingService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultPathFindingService implements PathfindingService {

    @Override
    public List<Location> findPath(final PathSuggestionParameterWrapper pathSuggestionParameterWrapper) {
        final PathFinder pathFinderAlgorithm = new AStarPathFinder(); // TODO Read this from a config later?
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
