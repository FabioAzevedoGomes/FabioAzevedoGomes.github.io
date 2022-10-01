package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.PredictorDao;
import com.example.accidentsRS.dao.factory.MongoQueryFactory;
import com.example.accidentsRS.dao.factory.MongoUpdateFactory;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.prediction.AggregatePredictorModel;
import com.example.accidentsRS.model.prediction.Predictor;
import com.example.accidentsRS.model.prediction.Region;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@Component
public class DefaultPredictorDao implements PredictorDao {

    @Autowired
    MongoQueryFactory defaultMongoQueryFactory;

    @Autowired
    MongoUpdateFactory defaultMongoUpdateFactory;

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public AggregatePredictorModel getPredictorByName(final String name)
            throws PersistenceException, IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        return mongoOperations.aggregate(
                defaultMongoQueryFactory.aggregatePredictiveModel(name),
                Predictor.class,
                AggregatePredictorModel.class
        ).getMappedResults().get(0).compile();
    }

    @Override
    public void savePredictor(final Predictor predictor, final List<Region> regionList) {
        mongoOperations.save(predictor);
        regionList.forEach(mongoOperations::save);
    }

    @Override
    public Region getRegionOfPointInModel(final Location point, final String modelName) {
        List<Region> list = mongoOperations.find(
                defaultMongoQueryFactory.createRegionFromModelIntersectsPointQuery(point, modelName),
                Region.class
        );

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public void updateRegionRiskIndexes(final List<Region> regionList) {
        BulkOperations bulkOperations = mongoOperations.bulkOps(BulkOperations.BulkMode.UNORDERED, Region.class);
        for (final Region region : regionList) {
            bulkOperations.updateOne(
                    defaultMongoQueryFactory.createIdMatchQueryForRegions(region.getRegionId()),
                    defaultMongoUpdateFactory.createRiskUpdate(region.getRisk())
            );
        }
        bulkOperations.execute();
    }

    @Override
    public List<String> getPredictorNames() {
        return mongoOperations.findDistinct(
                Predictor.NAME,
                Predictor.class,
                String.class
        );
    }

    @Override
    public List<Region> getPredictorRegions(final String modelName) {
        return mongoOperations.find(
                Query.query(Criteria.where(Region.PREDICTOR).is(modelName)),
                Region.class
        );
    }
}
