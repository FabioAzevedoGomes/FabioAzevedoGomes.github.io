package com.example.accidentsRS.dao;

import com.example.accidentsRS.model.ExtendedIntersectionModel;
import com.example.accidentsRS.model.IntersectionModel;
import org.springframework.data.geo.Point;

import java.util.List;

public interface IntersectionDao extends GenericDao<IntersectionModel> {
    ExtendedIntersectionModel getIntersectionDescription(final String externalId);
    List<IntersectionModel> getNearbyIntersections(final Point location, final int maxMatches);
}
