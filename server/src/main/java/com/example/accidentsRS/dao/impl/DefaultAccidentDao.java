package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.AccidentDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.AccidentModel;
import com.example.accidentsRS.model.Address;
import com.example.accidentsRS.model.DateTimeModel;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultAccidentDao extends AbstractDao<AccidentModel> implements AccidentDao {

    @Override
    public List<AccidentModel> get(final List<FilterWrapperModel> filters) {
        return super.get(filters, AccidentModel.class);
    }

    @Override
    public void update(final List<FilterWrapperModel> filters,
                       final List<UpdateModel> updateValues) throws PersistenceException {
        super.update(filters, updateValues, AccidentModel.class);
    }

    @Override
    public void delete(final List<FilterWrapperModel> filters) throws PersistenceException {
        super.delete(filters, AccidentModel.class);
    }

    @Override
    public List<String> findDistinctStringField(final String field) {
        return mongoOperations.findDistinct(field, AccidentModel.class, String.class);
    }

    @Override
    public List<java.util.Date> findDistinctDates() {
        return mongoOperations.findDistinct(
                AccidentModel.DATE + "." + DateTimeModel.DATE,
                AccidentModel.class,
                java.util.Date.class
        );
    }

    @Override
    public float findMaxLat() {
        return mongoOperations.findOne(
                defaultMongoQueryFactory.createSortedQuery(
                        false,
                        AccidentModel.ADDRESS + "." + Address.LOCATION + "." + Location.LATITUDE
                ),
                AccidentModel.class
        ).getAddress().getLocation().getLatitude();
    }

    @Override
    public float findMinLat() {
        return mongoOperations.findOne(
                defaultMongoQueryFactory.createSortedQuery(
                        true,
                        AccidentModel.ADDRESS + "." + Address.LOCATION + "." + Location.LATITUDE
                ),
                AccidentModel.class
        ).getAddress().getLocation().getLatitude();
    }

    @Override
    public float findMaxLon() {
        return mongoOperations.findOne(
                defaultMongoQueryFactory.createSortedQuery(
                        false,
                        AccidentModel.ADDRESS + "." + Address.LOCATION + "." + Location.LONGITUDE
                ),
                AccidentModel.class
        ).getAddress().getLocation().getLatitude();
    }

    @Override
    public float findMinLon() {
        return mongoOperations.findOne(
                defaultMongoQueryFactory.createSortedQuery(
                        true,
                        AccidentModel.ADDRESS + "." + Address.LOCATION + "." + Location.LONGITUDE
                ),
                AccidentModel.class
        ).getAddress().getLocation().getLatitude();
    }
}
