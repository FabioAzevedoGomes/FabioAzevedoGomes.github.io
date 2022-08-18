package com.example.accidentsRS.services.factory.impl;

import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.filter.OperationEnum;
import com.example.accidentsRS.services.factory.FilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class DefaultFilterFactory implements FilterFactory {

    private static final int TIME_OF_DAY_CONSTANT = 3600000;

    @Override
    public List<FilterWrapperModel> createFilterForDateTime(final Date dateTime) {
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

        final FilterWrapperModel dateFilter = new FilterWrapperModel(
                Collections.singletonList(AccidentModel.DATE + "." + Date.DATE),
                OperationEnum.IS,
                dateTime.getDate()
        );

        final FilterWrapperModel firstTimeFilter = new FilterWrapperModel(
                Collections.singletonList(AccidentModel.DATE + "." + Date.TIME_OF_DAY),
                OperationEnum.GREATER_OR_EQUAL,
                minToD
        );

        final FilterWrapperModel secondTimeFilter = new FilterWrapperModel(
                Collections.singletonList(AccidentModel.DATE + "." + Date.TIME_OF_DAY),
                OperationEnum.LESS_THAN,
                maxToD
        );

        return Arrays.asList(dateFilter, firstTimeFilter, secondTimeFilter);
    }

    @Override
    public List<FilterWrapperModel> createFilterForFieldValuePairs(final Map<String, Object> fieldValueMap) {
        List<FilterWrapperModel> filters = new ArrayList<>();
        if (!CollectionUtils.isEmpty(fieldValueMap)) {
            for (String key : fieldValueMap.keySet()) {
                filters.add(
                        new FilterWrapperModel(
                                Collections.singletonList(key),
                                OperationEnum.IS,
                                fieldValueMap.get(key)
                        )
                );
            }
        }
        return filters;
    }

    @Override
    public List<FilterWrapperModel> createFiltersForFindingStreetId(final String externalId) {
        return createFilterForFieldValuePairs(
                Collections.singletonMap(DirectionalStreetModel.DIRECTIONAL_ID, externalId)
        );
    }

    @Override
    public List<FilterWrapperModel> createFiltersForFindingIntersectionId(final String externalId) {
        return createFilterForFieldValuePairs(
                Collections.singletonMap(DirectionalStreetModel.EXTERNAL_ID, externalId)
        );
    }
}
