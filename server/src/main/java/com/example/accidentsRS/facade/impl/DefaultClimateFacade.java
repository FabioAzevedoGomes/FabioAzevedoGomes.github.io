package com.example.accidentsRS.facade.impl;

import com.example.accidentsRS.converter.ClimateConverter;
import com.example.accidentsRS.converter.ClimateReverseConverter;
import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.ClimateFacade;
import com.example.accidentsRS.facade.validator.FieldValidator;
import com.example.accidentsRS.services.ClimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultClimateFacade implements ClimateFacade {

    private static final Logger LOGGER = Logger.getLogger(DefaultClimateFacade.class.getName());

    @Autowired
    ClimateService defaultClimateService;

    @Autowired
    ClimateConverter defaultClimateConverter;

    @Autowired
    ClimateReverseConverter defaultClimateReverseConverter;

    @Override
    public void createClimateRecord(final ClimateData inputClimateData) throws PersistenceException {
        try {
            defaultClimateService.createClimateRecord(
                    defaultClimateConverter.convert(inputClimateData)
            );
        } catch (final ConversionException conversionException) {
            LOGGER.log(Level.SEVERE, "Error converting climate data, object was not persisted!", conversionException);
            throw new PersistenceException("Climate object was not persisted!", conversionException);
        }
    }

    @Override
    public List<ClimateData> findAllMatchingFilter(Map<String, Object> climateFilters) throws ValidationException {
        return defaultClimateReverseConverter.convertAll(
                defaultClimateService.findAllMatchingFilters(climateFilters)
        );
    }

    @Override
    public void updateAllMatchingFilter(Map<String, Object> climateFilters, Map<String, Object> newValue) throws ValidationException {
        // TODO
    }

    @Override
    public void deleteAllMatchingFilter(Map<String, Object> climateFilters) throws ValidationException {
        // TODO
    }
}
