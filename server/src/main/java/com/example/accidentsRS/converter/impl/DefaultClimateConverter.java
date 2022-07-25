package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.ClimateConverter;
import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.model.ClimateModel;
import org.springframework.stereotype.Component;

@Component
public class DefaultClimateConverter implements ClimateConverter {

    @Override
    public ClimateModel convert(final ClimateData climateData) {
        ClimateModel climateModel = new ClimateModel();
        climateModel.setDateTime(climateData.getDate_time());
        climateModel.setVisibility(climateData.getVisibility());
        climateModel.setPrecipitationMm(climateData.getPrecipitation_mm());
        climateModel.setRelativeHumidityPercentage(climateData.getRelative_humidity_percentage());
        climateModel.setAirTempCelsius(climateData.getAir_temp_celcius());
        climateModel.setWindSpeedMs(climateData.getWind_speed_ms());
        climateModel.setLocation(climateData.getLocation());
        return climateModel;
    }
}
