package com.example.accidentsRS.facade.impl;

import com.example.accidentsRS.converter.AccidentConverter;
import com.example.accidentsRS.converter.AccidentReverseConverter;
import com.example.accidentsRS.converter.FilterConverter;
import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.AccidentFacade;
import com.example.accidentsRS.services.AccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultAccidentFacade implements AccidentFacade {

    private static final Logger LOGGER = Logger.getLogger(DefaultAccidentFacade.class.getName());

    @Autowired
    AccidentService defaultAccidentService;

    @Autowired
    AccidentConverter defaultAccidentConverter;

    @Autowired
    AccidentReverseConverter defaultAccidentReverseConverter;

    @Autowired
    FilterConverter defaultFilterConverter;

    @Override
    public void createAccidentRecord(AccidentData inputAccidentData) throws PersistenceException {
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
    public List<AccidentData> findAllMatchingFilter(List<FilterWrapperData> accidentFilters) throws ValidationException {
        return defaultAccidentReverseConverter.convertAll(
                defaultAccidentService.findAllMatchingFilters(
                        defaultFilterConverter.convertAll(accidentFilters)
                )
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
