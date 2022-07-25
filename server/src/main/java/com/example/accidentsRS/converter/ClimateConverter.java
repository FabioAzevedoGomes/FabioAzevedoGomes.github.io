package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.model.ClimateModel;
import org.springframework.core.convert.converter.Converter;

public interface ClimateConverter extends Converter<ClimateData, ClimateModel> {
    ClimateModel convert(ClimateData climateData);
}
