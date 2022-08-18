package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.GenericDao;
import com.example.accidentsRS.dao.factory.MongoQueryFactory;
import com.example.accidentsRS.dao.factory.MongoUpdateFactory;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.PersistedModel;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.update.UpdateModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractDao<T extends PersistedModel> implements GenericDao<T> {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    MongoQueryFactory defaultMongoQueryFactory;

    @Autowired
    MongoUpdateFactory defaultMongoUpdateFactory;

    @Override
    public void save(final PersistedModel model) throws PersistenceException {
        try {
            mongoOperations.save(model);
        } catch (final IllegalArgumentException e) {
            throw new PersistenceException(String.format(
                    "Could not save %s to the database. Cause: %s",
                    model.getClass().getName(),
                    e.getMessage()
            ));
        }
    }

    @Override
    public abstract List<T> get(final List<FilterWrapperModel> filters);

    @Override
    public abstract void update(final List<FilterWrapperModel> filters, final List<UpdateModel> updateValues) throws PersistenceException;

    @Override
    public abstract void delete(final List<FilterWrapperModel> filters) throws PersistenceException;

    public List<T> get(final List<FilterWrapperModel> filters, final Class<T> clazz) {
        return mongoOperations.find(defaultMongoQueryFactory.createQueryFromFilters(filters), clazz);
    }

    public void update(final List<FilterWrapperModel> filters,
                       final List<UpdateModel> updateValues,
                       final Class<T> clazz) throws PersistenceException {
        final UpdateResult result = mongoOperations.updateMulti(
                defaultMongoQueryFactory.createQueryFromFilters(filters),
                defaultMongoUpdateFactory.createUpdateFromValues(updateValues),
                clazz
        );
        if (!result.wasAcknowledged()) {
            throw new PersistenceException("No values were updated!");
        }
        if (result.getMatchedCount() <= 0) {
            throw new PersistenceException("No values matched filters for update!");
        }
    }

    public void delete(final List<FilterWrapperModel> filters, final Class<T> clazz) throws PersistenceException {
        final DeleteResult result = mongoOperations.remove(
                defaultMongoQueryFactory.createQueryFromFilters(filters),
                clazz
        );

        if (!result.wasAcknowledged()) {
            throw new PersistenceException("No values were deleted!");
        }
        if (result.getDeletedCount() <= 0) {
            throw new PersistenceException("No values matched filters for delete!");
        }
    }

}
