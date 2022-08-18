package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.UpdateData;
import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public interface UpdateConverter extends Converter<UpdateData, UpdateModel> {
    UpdateModel convert(UpdateData updateData);
    List<UpdateModel> convertAll(List<UpdateData> updateDataList);
}
