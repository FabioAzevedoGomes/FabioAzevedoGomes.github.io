package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.model.filter.FilterWrapperModel;

import java.util.List;

public interface FilterConverter {
    FilterWrapperModel convert(FilterWrapperData filterWrapperData);
    List<FilterWrapperModel> convertAll(List<FilterWrapperData> filterWrapperDataList);
}
