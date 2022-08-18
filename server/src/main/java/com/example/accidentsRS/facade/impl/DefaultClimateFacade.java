package com.example.accidentsRS.facade.impl;

import com.example.accidentsRS.converter.ClimateConverter;
import com.example.accidentsRS.converter.ClimateReverseConverter;
import com.example.accidentsRS.converter.FilterConverter;
import com.example.accidentsRS.converter.UpdateConverter;
import com.example.accidentsRS.data.ClimateData;
import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.endpoints.data.UpdateWrapper;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.ClimateFacade;
import com.example.accidentsRS.services.ClimateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;

import java.util.List;
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

    @Autowired
    FilterConverter defaultFilterConverter;

    @Autowired
    UpdateConverter defaultUpdateConverter;

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
    public List<ClimateData> findAllMatchingFilter(final List<FilterWrapperData> climateFilters) throws ValidationException {
        return defaultClimateReverseConverter.convertAll(
                defaultClimateService.findAllMatchingFilters(
                        defaultFilterConverter.convertAll(climateFilters)
                )
        );
    }

    @Override
    public void updateAllMatchingFilter(final UpdateWrapper updateWrapper) throws ValidationException {
        defaultClimateService.updateAllMatchingFilters(
                defaultFilterConverter.convertAll(updateWrapper.getFilters()),
                defaultUpdateConverter.convertAll(updateWrapper.getUpdates())
        );
    }

    @Override
    public void deleteAllMatchingFilter(final List<FilterWrapperData> climateFilters) throws ValidationException {
        defaultClimateService.deleteAllMatchingFilters(
                defaultFilterConverter.convertAll(climateFilters)
        );
    }
}
