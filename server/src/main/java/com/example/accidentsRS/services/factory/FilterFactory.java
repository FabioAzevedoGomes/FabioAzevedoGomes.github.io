package com.example.accidentsRS.services.factory;

import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.filter.FilterWrapperModel;

import java.util.List;
import java.util.Map;

public interface FilterFactory {
    List<FilterWrapperModel> createFilterForDateTime(Date dateTime);
    List<FilterWrapperModel> createFilterForFieldValuePairs(Map<String, Object> fieldValueMap);
    List<FilterWrapperModel> createFiltersForFindingStreetId(final String externalId);
    List<FilterWrapperModel> createFiltersForFindingIntersectionId(final String externalId);
}
