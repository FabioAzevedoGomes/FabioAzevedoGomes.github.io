package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.StreetDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultStreetDao extends AbstractDao<DirectionalStreetModel> implements StreetDao {

    @Override
    public List<DirectionalStreetModel> get(final List<FilterWrapperModel> filters) {
        return super.get(filters, DirectionalStreetModel.class);
    }

    @Override
    public void update(final List<FilterWrapperModel> filters,
                       final List<UpdateModel> updateValues) throws PersistenceException {
        super.update(filters, updateValues, DirectionalStreetModel.class);
    }

    @Override
    public void delete(final List<FilterWrapperModel> filters) throws PersistenceException {
        super.delete(filters, DirectionalStreetModel.class);
    }

    @Override
    public List<DirectionalStreetModel> getNearbyStreets(final Point location, final int maxMatches) {
        return mongoOperations.find(
                defaultMongoQueryFactory.createLimitedNearQueryFor(location, maxMatches),
                DirectionalStreetModel.class
        );
    }

}
