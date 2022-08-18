package com.example.accidentsRS.dao.factory.impl;

import com.example.accidentsRS.dao.factory.MongoQueryFactory;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.ExtendedIntersectionModel;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.filter.OperationEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
