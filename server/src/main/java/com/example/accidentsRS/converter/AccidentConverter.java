package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.model.AccidentModel;
import org.springframework.core.convert.converter.Converter;

public interface AccidentConverter extends Converter<AccidentData, AccidentModel> {
    AccidentModel convert(AccidentData accidentData);
}
