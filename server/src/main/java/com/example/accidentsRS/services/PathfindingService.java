package com.example.accidentsRS.services;

import com.example.accidentsRS.data.PathSuggestionParameterWrapper;
import com.example.accidentsRS.model.Location;

import java.util.List;

public interface PathfindingService {
    List<Location> findPath(PathSuggestionParameterWrapper pathSuggestionParameterWrapper);
}
