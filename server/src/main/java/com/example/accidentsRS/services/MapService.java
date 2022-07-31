package com.example.accidentsRS.services;

import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.ExtendedIntersectionModel;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.model.IntersectionModel;
import org.springframework.data.geo.Point;

import java.util.List;

public interface MapService {

    void addStreet(DirectionalStreetModel directionalStreetModel);

    void addIntersection(IntersectionModel intersectionModel);

    ExtendedIntersectionModel getIntersectionDescription(String externalId);

    List<GeoLocation> findNearestPoints(Point point, int maxMatches);
}
