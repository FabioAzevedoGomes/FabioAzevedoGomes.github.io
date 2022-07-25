package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.model.Climate;
import com.example.accidentsRS.model.ClimateModel;
import com.example.accidentsRS.services.AccidentService;
import com.example.accidentsRS.services.ClimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DefaultClimateService implements ClimateService {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    AccidentService defaultAccidentService;

    @Override
    public void createClimateRecord(final ClimateModel climateModel) {
        // Save separately for other queries
        mongoOperations.save(climateModel); // TODO see if required

        // Update accidents with this information
        defaultAccidentService.updateWithClimateData(
                new Climate(
                        climateModel.getVisibility(),
                        climateModel.getRelativeHumidityPercentage(),
                        climateModel.getPrecipitationMm(),
                        climateModel.getWindSpeedMs(),
                        climateModel.getAirTempCelsius()
                ),
                climateModel.getDateTime()
        );
    }

    @Override
    public List<ClimateModel> findAllMatchingFilters(Map<String, Object> accidentFilters) {
        return null;
    }
}
