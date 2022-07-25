package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.ClimateReverseConverter;
import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.model.ClimateModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultClimateReverseConverter implements ClimateReverseConverter {

    @Override
    public ClimateData convert(ClimateModel climateModel) {
        ClimateData climateData = new ClimateData();
        climateData.setDate_time(climateModel.getDateTime());
        climateData.setVisibility(climateModel.getVisibility());
        climateData.setPrecipitation_mm(climateModel.getPrecipitationMm());
        climateData.setRelative_humidity_percentage(climateModel.getRelativeHumidityPercentage());
        climateData.setAir_temp_celcius(climateModel.getAirTempCelsius());
        climateData.setWind_speed_ms(climateModel.getWindSpeedMs());
        climateData.setLocation(climateModel.getLocation());
        return climateData;
    }

    @Override
    public List<ClimateData> convertAll(List<ClimateModel> climateModelList) {
        List<ClimateData> climateDataList = new ArrayList<>();
        for (ClimateModel climateModel : climateModelList) {
            climateDataList.add(convert(climateModel));
        }
        return climateDataList;
    }
}
