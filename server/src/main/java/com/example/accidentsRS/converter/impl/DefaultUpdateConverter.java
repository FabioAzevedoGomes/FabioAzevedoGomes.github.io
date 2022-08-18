package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.UpdateConverter;
import com.example.accidentsRS.data.UpdateData;
import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class DefaultUpdateConverter implements UpdateConverter {

    @Override
    public UpdateModel convert(final UpdateData updateData) {
        UpdateModel updateModel = new UpdateModel();
        updateModel.setValue(updateData.getValue());
        updateModel.setType(updateData.getType());
        updateModel.setField(updateData.getField());
        return updateModel;
    }

    @Override
    public List<UpdateModel> convertAll(final List<UpdateData> updateDataList) {
        List<UpdateModel> filterWrapperModelList = new ArrayList<>();
        if (nonNull(updateDataList)) {
            for (UpdateData filterWrapperData : updateDataList) {
                filterWrapperModelList.add(convert(filterWrapperData));
            }
        }
        return filterWrapperModelList;
    }
}
