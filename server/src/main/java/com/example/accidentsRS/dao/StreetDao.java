package com.example.accidentsRS.dao;

import com.example.accidentsRS.model.DirectionalStreetModel;
import org.springframework.data.geo.Point;

import java.util.List;

public interface StreetDao extends GenericDao<DirectionalStreetModel> {
    List<DirectionalStreetModel> getNearbyStreets(Point location, int maxMatches);
}
