package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.dao.AccidentDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.model.Climate;
import com.example.accidentsRS.model.Date;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import com.example.accidentsRS.model.update.UpdateTypeEnum;
import com.example.accidentsRS.services.AccidentService;
import com.example.accidentsRS.services.factory.FilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultAccidentService implements AccidentService {

    private static final Logger LOGGER = Logger.getLogger(DefaultAccidentService.class.getName());

    @Autowired
    FilterFactory defaultFilterFactory;

    @Autowired
    AccidentDao defaultAccidentDao;

    @Override
    public void createAccidentRecord(final AccidentModel accidentModel) {
        try {
            defaultAccidentDao.save(accidentModel);
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public List<AccidentModel> findAllMatchingFilters(final List<FilterWrapperModel> accidentFilters) {
        final List<AccidentModel> accidentModelList = defaultAccidentDao.get(accidentFilters);
        if (CollectionUtils.isEmpty(accidentModelList)) {
            LOGGER.log(Level.WARNING, "No accidents match given filters");
        }
        return accidentModelList;
    }

    @Override
    public void updateAllMatchingFilters(final List<FilterWrapperModel> accidentFilters, final List<UpdateModel> updateValues) {
        try {
            defaultAccidentDao.update(accidentFilters, updateValues);
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void deleteAllMatchingFilters(final List<FilterWrapperModel> accidentFilters) {
        try {
            defaultAccidentDao.delete(accidentFilters);
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void updateWithClimateData(final Climate climate, final Date dateTime) {
        List<UpdateModel> accidentUpdate = new ArrayList<>();
        accidentUpdate.add(new UpdateModel(AccidentModel.CLIMATE, UpdateTypeEnum.SET, climate));

        this.updateAllMatchingFilters(
                defaultFilterFactory.createFilterForDateTime(dateTime),
                accidentUpdate
        );
    }

}
