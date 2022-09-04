package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.ClimateDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.ClimateModel;
import com.example.accidentsRS.model.DateTimeModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DefaultClimateDao extends AbstractDao<ClimateModel> implements ClimateDao {

    @Override
    public void save(ClimateModel model) throws PersistenceException {

    }

    @Override
    public List<ClimateModel> get(final List<FilterWrapperModel> filters) {
        return super.get(filters, ClimateModel.class);
    }

    @Override
    public void update(final List<FilterWrapperModel> filters,
                       final List<UpdateModel> updateValues) throws PersistenceException {
        super.update(filters, updateValues, ClimateModel.class);
    }

    @Override
    public void delete(final List<FilterWrapperModel> filters) throws PersistenceException {
        super.delete(filters, ClimateModel.class);
    }

    @Override
    public List<ClimateModel> getClimateOnDate(final Date date) {
        return mongoOperations.find(
                defaultMongoQueryFactory.createWithinSpaceTimeQuery(
                        null,
                        date,
                        null,
                        ClimateModel.DATE_TIME + "." + DateTimeModel.DATE
                ),
                ClimateModel.class
        );
    }

    @Override
    public List<ClimateModel> getLatest() {
        return mongoOperations.find(
                defaultMongoQueryFactory.createLatestQuery(),
                ClimateModel.class
        );
    }
}
