package com.example.accidentsRS.services.factory.impl;

import com.example.accidentsRS.model.update.UpdateModel;
import com.example.accidentsRS.model.update.UpdateTypeEnum;
import com.example.accidentsRS.services.factory.UpdateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DefaultUpdateFactory implements UpdateFactory {

    protected UpdateModel createFromValues(final String fieldName, final Object value, final UpdateTypeEnum type) {
        return new UpdateModel(fieldName, type, value);
    }

    @Override
    public UpdateModel createSetUpdateForFieldAndValue(final String fieldName, final Object value) {
        return createFromValues(fieldName, value, UpdateTypeEnum.SET);
    }

    @Override
    public UpdateModel createPushUpdateForFieldAndValue(final String fieldName, final Object value) {
        return createFromValues(fieldName, value, UpdateTypeEnum.PUSH);
    }

    @Override
    public List<UpdateModel> createPushUpdatesForFieldValuePairs(final Map<String, Object> fieldValueMap) {
        List<UpdateModel> updateList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(fieldValueMap)) {
            for (final String key : fieldValueMap.keySet()) {
                updateList.add(createPushUpdateForFieldAndValue(key, fieldValueMap.get(key)));
            }
        }
        return updateList;
    }
}
