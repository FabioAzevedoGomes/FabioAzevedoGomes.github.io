package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.IntersectionDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.ExtendedIntersectionModel;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultIntersectionDao extends AbstractDao<IntersectionModel> implements IntersectionDao {

    @Override
    public List<IntersectionModel> get(final List<FilterWrapperModel> filters) {
        return super.get(filters, IntersectionModel.class);
    }

    @Override
    public void update(final List<FilterWrapperModel> filters,
                       final List<UpdateModel> updateValues) throws PersistenceException {
        super.update(filters, updateValues, IntersectionModel.class);
    }

    @Override
    public void delete(final List<FilterWrapperModel> filters) throws PersistenceException {
        super.delete(filters, IntersectionModel.class);
    }

    @Override
    public ExtendedIntersectionModel getIntersectionDescription(final String externalId) {
        return mongoOperations.aggregate(
                defaultMongoQueryFactory.aggregateIntersectionAndStreets(externalId),
                IntersectionModel.class,
                ExtendedIntersectionModel.class
        ).getMappedResults().get(0);
    }

    @Override
    public List<IntersectionModel> getNearbyIntersections(Point location, int maxMatches) {
        return mongoOperations.find(
                defaultMongoQueryFactory.createLimitedNearQueryFor(location, maxMatches),
                IntersectionModel.class
        );
    }
}
