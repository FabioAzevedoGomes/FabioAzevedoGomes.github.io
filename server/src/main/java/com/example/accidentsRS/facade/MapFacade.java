package com.example.accidentsRS.facade;

import com.example.accidentsRS.data.DirectionalStreetData;
import com.example.accidentsRS.data.GeoPointData;
import com.example.accidentsRS.data.IntersectionData;
import com.example.accidentsRS.data.PathSuggestionParameterWrapper;
import com.example.accidentsRS.model.Location;

import java.util.List;

public interface MapFacade {
    void addStreet(DirectionalStreetData directionalStreetData);
    void addIntersection(IntersectionData intersectionData);
    List<GeoPointData> findNearestPoints(Location location);
    List<Location> suggestPath(PathSuggestionParameterWrapper pathSuggestionParameter);
}
