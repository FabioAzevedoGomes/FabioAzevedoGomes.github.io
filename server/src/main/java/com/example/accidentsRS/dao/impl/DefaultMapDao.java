package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.MapDao;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.model.IntersectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultMapDao implements MapDao {

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public IntersectionModel getIntersection(final String externalId) {
        return mongoOperations.findOne(
                Query.query(Criteria.where("externalId").is(externalId)), IntersectionModel.class
        );
    }

    @Override
    public DirectionalStreetModel getStreet(final String directionalId) {
        return mongoOperations.findOne(
                Query.query(Criteria.where("directionalId").is(directionalId)), DirectionalStreetModel.class
        );

    }

    @Override
    public List<IntersectionModel> getIntersections(final List<String> externalIds) {
        return mongoOperations.find(
                Query.query(Criteria.where("externalId").in(externalIds)), IntersectionModel.class
        );

    }

    @Override
    public List<DirectionalStreetModel> getStreets(final List<String> directionalIds) {
        return mongoOperations.find(
                Query.query(Criteria.where("directionalId").in(directionalIds)), DirectionalStreetModel.class
        );
    }

    @Override
    public Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getCircleAroundIntersection(String externalId, float radius) {
        return getCircleAround(getIntersection(externalId), radius);
    }

    @Override
    public Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getCircleAroundStreet(String directionalId, float radius) {
        return getCircleAround(getStreet(directionalId), radius);
    }

    protected Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getCircleAround(final GeoLocation point, final float radius) {
        final Circle shape = new Circle(
                new Point(
                        point.getLocation().getLongitude(),
                        point.getLocation().getLatitude()),
                radius
        );
        return Pair.of(
                mongoOperations.find(Query.query(Criteria.where("location").withinSphere(shape)), IntersectionModel.class),
                mongoOperations.find(Query.query(Criteria.where("location").withinSphere(shape)), DirectionalStreetModel.class)
        );
    }

}
