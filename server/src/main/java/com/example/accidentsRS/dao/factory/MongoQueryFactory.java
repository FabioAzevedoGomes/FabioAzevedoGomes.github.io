package com.example.accidentsRS.dao.factory;

import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.prediction.Bounds;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

public interface MongoQueryFactory {
    Query createQueryFromFilters(List<FilterWrapperModel> filters);
    Aggregation aggregateIntersectionAndStreets(String intersectionId);
    Query createLimitedNearQueryFor(Point location, int maxMatches);
    Query createSortedQuery(boolean ascending, String field);
    Aggregation aggregatePredictiveModel(String modelName);
    Query createRegionFromModelIntersectsPointQuery(Location point, String modelName);
    Query createWithinSpaceTimeQuery(Bounds bounds, Date date, String coordinateFieldName, String dateFieldName);
    Query createIdMatchQueryForRegions(final String regionId);
    Query createLatestQuery();
}
