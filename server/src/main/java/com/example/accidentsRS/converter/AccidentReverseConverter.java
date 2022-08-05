package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.model.AccidentModel;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public interface AccidentReverseConverter extends Converter<AccidentModel, AccidentData> {
    AccidentData convert(AccidentModel accidentModel);
    List<AccidentData> convertAll(List<AccidentModel> accidentModelModelList);
}
