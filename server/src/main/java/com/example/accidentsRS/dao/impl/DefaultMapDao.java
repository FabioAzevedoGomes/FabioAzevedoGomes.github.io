package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.MapDao;
import com.example.accidentsRS.dao.PredictorDao;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.prediction.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultMapDao implements MapDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMapDao.class);

    @Autowired
    private PredictorDao defaultPredictorDao;

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
    public DirectionalStreetModel getStreetWithRisk(final String directionalId, final String modelName) {
        final DirectionalStreetModel street = mongoOperations.findOne(
                Query.query(Criteria.where(DirectionalStreetModel.DIRECTIONAL_ID).is(directionalId)),
                DirectionalStreetModel.class
        );
        if (street != null) {
            final Region region = defaultPredictorDao.getRegionOfPointInModel(street.getLocation(), modelName);
            if (region != null) {
                street.setRisk(region.getRisk());
            } else {
                street.setRisk(0.0f);
            }
        }
        return street;
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

    protected List<IntersectionModel> getAllIntersectionsInPolygon(final Shape polygon) {
        return mongoOperations.find(
                Query.query(Criteria.where(IntersectionModel.LOCATION).within(polygon)
                ),
                IntersectionModel.class
        );
    }

    protected List<DirectionalStreetModel> getAllStreetsInPolygon(final Shape polygon) {
        return mongoOperations.find(
                Query.query(Criteria.where(DirectionalStreetModel.LOCATION).within(polygon)
                ),
                DirectionalStreetModel.class
        );
    }

    protected Shape createShapeFromRegion(final Region region) {
        return new GeoJsonPolygon(region.getBounds().getCoordinates().get(0).stream().map(list ->
                new Point(list.get(1), list.get(0))).collect(Collectors.toList())
        );
    }

    protected Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getForModelRegion(
            final Region region
    ) {
        final Shape polygon = createShapeFromRegion(region);
        final List<IntersectionModel> intersectionModels = getAllIntersectionsInPolygon(polygon);
        final List<DirectionalStreetModel> streetModels = getAllStreetsInPolygon(polygon);

        if (!CollectionUtils.isEmpty(streetModels)) {
            streetModels.forEach(streetModel -> streetModel.setRisk(region.getRisk()));
        }

        return Pair.of(intersectionModels, streetModels);
    }

    protected Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getRegionAroundPointWithRisk(
            final GeoLocation point,
            final String modelName
    ) {
        final Region region = defaultPredictorDao.getRegionOfPointInModel(point.getLocation(), modelName);
        if (region != null) {
            return getForModelRegion(region);
        } else {
            return getCircleAround(point, 1.0f);
        }

    }

    @Override
    public Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getRegionAroundIntersectionWithRisk(
            final String externalId,
            final String modelName
    ) {
        return getRegionAroundPointWithRisk(getIntersection(externalId), modelName);
    }

    @Override
    public Pair<List<IntersectionModel>, List<DirectionalStreetModel>> getRegionAroundStreetWithRisk(
            final String directionalId,
            final String modelName
    ) {
        return getRegionAroundPointWithRisk(getStreet(directionalId), modelName);
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
    public List<DirectionalStreetModel> getAllStreetsWithRisk(final String modelName) {
        final List<Region> allRegions = defaultPredictorDao.getPredictorRegions(modelName);
        final List<DirectionalStreetModel> allStreets = new ArrayList<>();

        allRegions.forEach(region -> {
            Shape poly = createShapeFromRegion(region);
            List<DirectionalStreetModel> streets = getAllStreetsInPolygon(poly);
            streets.forEach(street -> street.setRisk(region.getRisk()));
            allStreets.addAll(streets);
        });

        getAllStreets().stream()
                .filter(street -> !allStreets.contains(street))
                .forEach(street -> {
                    street.setRisk(0.0f);
                    allStreets.add(street);
                });

        return allStreets;
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

    public PredictorDao getDefaultPredictorDao() {
        return defaultPredictorDao;
    }

    public void setDefaultPredictorDao(PredictorDao defaultPredictorDao) {
        this.defaultPredictorDao = defaultPredictorDao;
    }
}
