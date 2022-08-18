package com.example.accidentsRS.dao.factory;

import com.example.accidentsRS.model.filter.FilterWrapperModel;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface MongoQueryFactory {
    Query createQueryFromFilters(List<FilterWrapperModel> filters);
    Aggregation aggregateIntersectionAndStreets(String intersectionId);
    Query createLimitedNearQueryFor(Point location, int maxMatches);
    Query createSortedQuery(boolean ascending, String field);
}
