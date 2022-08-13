package com.example.accidentsRS.services.factory.impl;

import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.filter.OperationEnum;
import com.example.accidentsRS.services.factory.QueryFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultQueryFactory implements QueryFactory {

    private static final int TIME_OF_DAY_CONSTANT = 3600000;

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
            case BEFORE:
            case LESS_OR_EQUAL:
                searchCriteria = searchCriteria.lte(value);
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
    public Query createTimeQuery(Date dateTime) {
        Query timeQuery = new Query();
        long measurementToD = dateTime.getTime_of_day();
        long minToD = measurementToD;
        long maxToD = measurementToD;

        if (measurementToD == 0) {
            maxToD = 6 * TIME_OF_DAY_CONSTANT;
        } else if (measurementToD == 12 * TIME_OF_DAY_CONSTANT) {
            minToD = 6 * TIME_OF_DAY_CONSTANT;
            maxToD = 15 * TIME_OF_DAY_CONSTANT;
        } else if (measurementToD == 18 * TIME_OF_DAY_CONSTANT) {
            minToD = 15 * TIME_OF_DAY_CONSTANT;
            maxToD = 24 * TIME_OF_DAY_CONSTANT;
        }

        timeQuery.addCriteria(Criteria.where("date.date").is(dateTime.getDate()));
        timeQuery.addCriteria(
                Criteria.where("date.time_of_day").gte(minToD)
                        .andOperator(Criteria.where("date.time_of_day").lt(maxToD))
        );

        return timeQuery;
    }
}
