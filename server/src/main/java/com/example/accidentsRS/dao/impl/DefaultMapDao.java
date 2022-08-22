package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.MapDao;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultMapDao implements MapDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMapDao.class);

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public IntersectionModel getIntersection(final String externalId) {
        return mongoOperations.findOne(
                Query.query(Criteria.where(IntersectionModel.EXTERNAL_ID).is(externalId)),
                IntersectionModel.class
        );
    }

    @Override
    public DirectionalStreetModel getStreet(final String directionalId) {
        return mongoOperations.findOne(
                Query.query(Criteria.where(DirectionalStreetModel.DIRECTIONAL_ID).is(directionalId)),
                DirectionalStreetModel.class
        );

    }

    @Override
    public List<IntersectionModel> getIntersections(final List<String> externalIds) {
        return mongoOperations.find(
                Query.query(Criteria.where(IntersectionModel.EXTERNAL_ID).in(externalIds)),
                IntersectionModel.class
        );

    }

    @Override
    public List<DirectionalStreetModel> getStreets(final List<String> directionalIds) {
        return mongoOperations.find(
                Query.query(Criteria.where(DirectionalStreetModel.DIRECTIONAL_ID).in(directionalIds)),
                DirectionalStreetModel.class
        );
    }

    @Override
    public Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getCircleAroundIntersection(final String externalId, float radiusKilometers) {
        return getCircleAround(getIntersection(externalId), radiusKilometers);
    }

    @Override
    public Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getCircleAroundStreet(final String directionalId, float radiusKilometers) {
        return getCircleAround(getStreet(directionalId), radiusKilometers);
    }

    @Override
    public List<IntersectionModel> getAllIntersections() {
        return mongoOperations.findAll(IntersectionModel.class);
    }

    @Override
    public List<DirectionalStreetModel> getAllStreets() {
        return mongoOperations.findAll(DirectionalStreetModel.class);
    }

    @Override
    public Location getRegionCenterFor(final Location location) {
        return null;
    }

    protected Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getCircleAround(final GeoLocation point, final float radiusKilometers) {
        final Circle shape = new Circle(
                new Point(
                        point.getLocation().getLongitude(),
                        point.getLocation().getLatitude()),
                new Distance(radiusKilometers, Metrics.KILOMETERS)
        );
        return Pair.of(
                mongoOperations.find(
                        Query.query(Criteria.where(IntersectionModel.LOCATION).withinSphere(shape)),
                        IntersectionModel.class
                ),
                mongoOperations.find(
                        Query.query(Criteria.where(DirectionalStreetModel.LOCATION).withinSphere(shape)),
                        DirectionalStreetModel.class
                )
        );
    }

}
