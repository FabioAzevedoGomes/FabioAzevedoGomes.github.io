package com.example.accidentsRS.services.factory;

import com.example.accidentsRS.model.update.UpdateModel;

import java.util.List;
import java.util.Map;

public interface UpdateFactory {
    UpdateModel createSetUpdateForFieldAndValue(String fieldName, Object value);
    UpdateModel createPushUpdateForFieldAndValue(String fieldName, Object value);
    List<UpdateModel> createPushUpdatesForFieldValuePairs(Map<String, Object> fieldValueMap);
}
