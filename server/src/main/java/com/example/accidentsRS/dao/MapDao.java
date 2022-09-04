package com.example.accidentsRS.dao;

import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.model.Location;
import org.springframework.data.util.Pair;

import java.util.List;

public interface MapDao {
    IntersectionModel getIntersection(String externalId);

    DirectionalStreetModel getStreet(String directionalId);

    List<IntersectionModel> getIntersections(List<String> externalIds);

    List<DirectionalStreetModel> getStreets(List<String> directionalIds);

    Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getCircleAroundIntersection(String externalId, float radiusKilometers);

    Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getCircleAroundStreet(String directionalId, float radiusKilometers);

    Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getRegionAroundIntersectionWithRisk(String externalId, final String modelName);

    Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getRegionAroundStreetWithRisk(String directionalId, final String modelName);

    List<IntersectionModel> getAllIntersections();

    List<DirectionalStreetModel> getAllStreets();

    Location getRegionCenterFor(Location location);
}
