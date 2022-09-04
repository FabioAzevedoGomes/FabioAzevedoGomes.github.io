package com.example.accidentsRS.dao.factory.impl;

import com.example.accidentsRS.dao.factory.MongoQueryFactory;
import com.example.accidentsRS.model.*;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.filter.OperationEnum;
import com.example.accidentsRS.model.prediction.AggregatePredictorModel;
import com.example.accidentsRS.model.prediction.Bounds;
import com.example.accidentsRS.model.prediction.Predictor;
import com.example.accidentsRS.model.prediction.Region;
import com.mongodb.lang.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class DefaultMongoQueryFactory implements MongoQueryFactory {

    protected Criteria createSingleFieldCriteria(final String fieldName, final OperationEnum operation, final Object value) {
        Criteria searchCriteria = Criteria.where(fieldName);
        switch (operation) {
            case EQUALS:
            case IS:
            case HAS:
                searchCriteria = searchCriteria.is(value);
                break;
            case IS_NOT:
            case DOES_NOT_HAVE:
                searchCriteria = searchCriteria.ne(value);
                break;
            case GREATER_OR_EQUAL:
            case AFTER:
                searchCriteria = searchCriteria.gte(value);
                break;
            case GREATER_THAN:
                searchCriteria = searchCriteria.gt(value);
                break;
            case BEFORE:
            case LESS_OR_EQUAL:
                searchCriteria = searchCriteria.lte(value);
                break;
            case LESS_THAN:
                searchCriteria = searchCriteria.lt(value);
                break;
            default:
                break;
        }
        return searchCriteria;
    }

    protected Criteria createMultiFieldOrCriteria(final List<String> fieldNames, final OperationEnum operation, final Object value) {
        List<Criteria> searchCriterion = new ArrayList<>();
        for (String fieldName : fieldNames) {
            searchCriterion.add(createSingleFieldCriteria(fieldName, operation, value));
        }
        return Criteria.where("").orOperator(searchCriterion);
    }

    protected Criteria resolveFilter(final String fieldName, final List<FilterWrapperModel> filters) {
        Criteria searchCriteria = Criteria.where("");
        List<Criteria> fieldCriterion = new ArrayList<>();
        for (FilterWrapperModel filterWrapperModel : filters) {
            fieldCriterion.add(createSingleFieldCriteria(
                    fieldName,
                    filterWrapperModel.getOperation(),
                    filterWrapperModel.getValue())
            );
        }

        if (!CollectionUtils.isEmpty(fieldCriterion) && fieldCriterion.size() == 1) {
            return fieldCriterion.get(0);
        } else if (!CollectionUtils.isEmpty(fieldCriterion)) {
            return searchCriteria.andOperator(fieldCriterion);
        } else {
            return Criteria.where("");
        }
    }

    @Override
    public Query createQueryFromFilters(final List<FilterWrapperModel> filters) {
        Query query = new Query();

        Map<String, List<FilterWrapperModel>> filtersMap = new HashMap<>();
        filters.stream()
                .filter(filter -> filter.getFields().size() == 1)
                .forEach(filter -> {
                    if (!filtersMap.containsKey(filter.getFields().get(0))) {
                        filtersMap.put(filter.getFields().get(0), new ArrayList<>());
                    }
                    filtersMap.get(filter.getFields().get(0)).add(filter);
                });

        for (String fieldName : filtersMap.keySet()) {
            query.addCriteria(resolveFilter(fieldName, filtersMap.get(fieldName)));
        }

        filters.stream()
                .filter(filter -> filter.getFields().size() > 1)
                .forEach(filter -> query.addCriteria(createMultiFieldOrCriteria(
                        filter.getFields(),
                        filter.getOperation(),
                        filter.getValue()
                )));

        return query;
    }

    @Override
    public Aggregation aggregateIntersectionAndStreets(final String intersectionId) {
        return Aggregation.newAggregation(
                new MatchOperation(Criteria.where(IntersectionModel.EXTERNAL_ID).is(intersectionId)),
                LookupOperation.newLookup()
                        .from(DirectionalStreetModel.COLLECTION_NAME)
                        .localField(IntersectionModel.CONNECTED_STREET_IDS)
                        .foreignField(DirectionalStreetModel.DIRECTIONAL_ID)
                        .as(ExtendedIntersectionModel.CONNECTED_STREET_IDS)
        );
    }

    @Override
    public Query createLimitedNearQueryFor(Point location, int maxMatches) {
        return Query.query(
                Criteria.where(IntersectionModel.LOCATION).near(location)
        ).limit(maxMatches);
    }

    protected Sort createDescendingSort(final String field) {
        return Sort.by(field).descending();
    }

    protected Sort createAscendingSort(final String field) {
        return Sort.by(field).ascending();
    }

    public Query createSortedQuery(final boolean ascending, final String field) {
        Sort sort = null;
        if (ascending) {
            sort = createAscendingSort(field);
        } else {
            sort = createDescendingSort(field);
        }
        return new Query().limit(1).with(sort);
    }

    @Override
    public Aggregation aggregatePredictiveModel(final String modelName) {
        return Aggregation.newAggregation(
                new MatchOperation(Criteria.where(Predictor.NAME).is(modelName)),
                LookupOperation.newLookup()
                        .from(Region.COLLECTION_NAME)
                        .localField(Predictor.DOMAIN)
                        .foreignField(Region.REGION_ID)
                        .as(AggregatePredictorModel.POPULATED_DOMAIN)
        );
    }

    @Override
    public Query createRegionFromModelIntersectsPointQuery(Location point, String modelName) {
        final GeoJsonPoint geoPoint = new GeoJsonPoint(new Point(point.getLatitude(), point.getLongitude()));
        return Query.query(
                Criteria.where(Region.PREDICTOR).is(modelName).andOperator(
                        Criteria.where(Region.BOUNDS + "." + Bounds.COORDINATES).intersects(geoPoint))
        );
    }

    protected Criteria getWithinSpaceCriteria(final Bounds bounds, final String coordinateFieldName) {
        final Shape boundsShape = new Polygon(bounds.getCoordinates()
                .stream()
                .map(list -> new Point(list.get(0), list.get(1)))
                .collect(Collectors.toList())
        );
        return Criteria.where(coordinateFieldName).within(boundsShape);
    }

    protected List<Criteria> getWithinTimeCriteria(final Date date, final String dateFieldName) {
        final Date dateWithoutTime = new Date(date.getTime());
        dateWithoutTime.setHours(0);
        dateWithoutTime.setMinutes(0);
        dateWithoutTime.setSeconds(0);
        final Date dateWithMaxTime = new Date(date.getTime());
        dateWithMaxTime.setHours(23);
        dateWithMaxTime.setMinutes(59);
        dateWithMaxTime.setSeconds(59);
        List<Criteria> list = new ArrayList<>();
        list.add(Criteria.where(dateFieldName).lte(dateWithMaxTime));
        list.add(Criteria.where(dateFieldName).gte(dateWithoutTime));
        return list;
    }

    @Override
    public Query createWithinSpaceTimeQuery(@Nullable Bounds bounds, final Date date, final String coordinateFieldName, final String dateFieldName) {
        Criteria criterion = Criteria.where("");
        List<Criteria> criteriaList = new ArrayList<>(getWithinTimeCriteria(date, dateFieldName));

        if (nonNull(bounds)) {
            criteriaList.add(getWithinSpaceCriteria(bounds, coordinateFieldName));
        }

        criterion = criterion.andOperator(criteriaList);
        return Query.query(criterion);
    }

    @Override
    public Query createIdMatchQueryForRegions(final String regionId) {
        return Query.query(Criteria.where(Region.REGION_ID).is(regionId));
    }

    @Override
    public Query createLatestQuery() {
        Query query = new Query();
        query.with(Sort.by(ClimateModel.DATE_TIME + "." + DateTimeModel.DATE).descending());
        query.limit(1);
        return query;
    }
}
