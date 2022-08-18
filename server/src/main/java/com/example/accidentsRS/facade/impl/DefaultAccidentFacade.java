package com.example.accidentsRS.facade.impl;

import com.example.accidentsRS.converter.AccidentConverter;
import com.example.accidentsRS.converter.AccidentReverseConverter;
import com.example.accidentsRS.converter.FilterConverter;
import com.example.accidentsRS.converter.UpdateConverter;
import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.endpoints.data.UpdateWrapper;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.AccidentFacade;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.services.AccidentService;
import com.example.accidentsRS.services.ValueSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultAccidentFacade implements AccidentFacade {

    private static final Logger LOGGER = Logger.getLogger(DefaultAccidentFacade.class.getName());

    @Autowired
    AccidentConverter defaultAccidentConverter;

    @Autowired
    AccidentReverseConverter defaultAccidentReverseConverter;

    @Autowired
    FilterConverter defaultFilterConverter;

    @Autowired
    UpdateConverter defaultUpdateConverter;

    @Autowired
    ValueSearchService defaultValueSearchService;

    @Autowired
    AccidentService defaultAccidentService;

    @Override
    public void createAccidentRecord(final AccidentData inputAccidentData) throws PersistenceException {
        try {
            defaultAccidentService.createAccidentRecord(
                    defaultAccidentConverter.convert(inputAccidentData)
            );
        } catch (final ConversionException conversionException) {
            LOGGER.log(Level.SEVERE, "Error converting accident data, object was not persisted!", conversionException);
            throw new PersistenceException("Accident object was not persisted!", conversionException);
        }
    }

    @Override
    public List<AccidentData> findAllMatchingFilter(final List<FilterWrapperData> accidentFilters) throws ValidationException {
        return defaultAccidentReverseConverter.convertAll(
                defaultAccidentService.findAllMatchingFilters(
                        defaultFilterConverter.convertAll(accidentFilters)
                )
        );
    }

    @Override
    public void updateAllMatchingFilter(final UpdateWrapper updateWrapper) throws ValidationException {
        defaultAccidentService.updateAllMatchingFilters(
                defaultFilterConverter.convertAll(updateWrapper.getFilters()),
                defaultUpdateConverter.convertAll(updateWrapper.getUpdates())
        );
    }

    @Override
    public void deleteAllMatchingFilter(final List<FilterWrapperData> accidentFilters) throws ValidationException {
        defaultAccidentService.deleteAllMatchingFilters(
                defaultFilterConverter.convertAll(accidentFilters)
        );
    }

    @Override
    public List<String> getAllAccidentTypes() {
        return defaultValueSearchService.getAllAccidentTypes();
    }

    @Override
    public List<String> getAllVehicleTypes() {
        return defaultValueSearchService.getAllVehicleTypes();
    }

    @Override
    public List<String> getAllRegions() {
        return defaultValueSearchService.getAllRegions();
    }

    @Override
    public List<Date> getAllDates() {
        return defaultValueSearchService.getAllDates();
    }

    @Override
    public Pair<Location, Location> getBoundingBox() {
        return defaultValueSearchService.getBoundingBox();
    }
}
