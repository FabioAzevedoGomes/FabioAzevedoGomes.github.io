package com.example.accidentsRS.services.factory;

import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


public interface QueryFactory {
    Query createQueryFromFilters(List<FilterWrapperModel> filters);
    Query createTimeQuery(Date dateTime);
}
