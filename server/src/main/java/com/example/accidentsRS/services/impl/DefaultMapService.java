package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class DefaultMapService implements MapService {

    @Autowired
    MongoOperations mongoOperations;

    private Query createIntersectionUpdateQuery(final DirectionalStreetModel directionalStreetModel) {
        return Query.query(
                Criteria.where("").orOperator(
                        Criteria.where("externalId").is(directionalStreetModel.getSourceIntersectionId()),
                        Criteria.where("externalId").is(directionalStreetModel.getDestinationIntersectionId())
                )
        );
    }

    private Update createIntersectionUpdateObject(final DirectionalStreetModel directionalStreetModel) {
        final Update update = new Update();
        update.push("connectedStreetIds", directionalStreetModel.getExternalId());
        return update;
    }

    @Override
    public void addStreet(final DirectionalStreetModel directionalStreetModel) {
        mongoOperations.save(directionalStreetModel);
        mongoOperations.updateMulti(
                createIntersectionUpdateQuery(directionalStreetModel),
                createIntersectionUpdateObject(directionalStreetModel),
                IntersectionModel.class
        );
    }

    @Override
    public void addIntersection(final IntersectionModel intersectionModel) {
        mongoOperations.save(intersectionModel);
    }
}
