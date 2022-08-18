package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.dao.ClimateDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.Climate;
import com.example.accidentsRS.model.ClimateModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import com.example.accidentsRS.services.AccidentService;
import com.example.accidentsRS.services.ClimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultClimateService implements ClimateService {

    private static final Logger LOGGER = Logger.getLogger(DefaultClimateService.class.getName());

    @Autowired
    AccidentService defaultAccidentService;

    @Autowired
    ClimateDao defaultClimateDao;

    @Override
    public void createClimateRecord(final ClimateModel climateModel) {
        try {
            defaultClimateDao.save(climateModel);
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
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<ClimateModel> findAllMatchingFilters(final List<FilterWrapperModel> climateFilters) {
        return defaultClimateDao.get(climateFilters);
    }

    @Override
    public void updateAllMatchingFilters(List<FilterWrapperModel> climateFilters, List<UpdateModel> updateValues) {
        try {
            defaultClimateDao.update(climateFilters, updateValues);
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void deleteAllMatchingFilters(List<FilterWrapperModel> climateFilters) {
        try {
            defaultClimateDao.delete(climateFilters);
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }
}
