package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.model.ClimateModel;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public interface ClimateReverseConverter extends Converter<ClimateModel, ClimateData> {
    ClimateData convert(ClimateModel climateData);
    List<ClimateData> convertAll(List<ClimateModel> climateModelList);
}