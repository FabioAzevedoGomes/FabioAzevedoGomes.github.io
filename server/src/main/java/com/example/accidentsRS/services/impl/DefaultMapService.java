package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.model.ExtendedIntersectionModel;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.services.MapService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
public class DefaultMapService implements MapService {

    @Autowired
    MongoOperations mongoOperations;

    private Query createIntersectionFromUpdateQuery(final DirectionalStreetModel directionalStreetModel) {
        return Query.query(Criteria.where("externalId").is(directionalStreetModel.getSourceIntersectionId()));
    }

    private Query createIntersectionToUpdateQuery(final DirectionalStreetModel directionalStreetModel) {
        return Query.query(Criteria.where("externalId").is(directionalStreetModel.getDestinationIntersectionId()));
    }

    private Update createIntersectionFromUpdateObject(final DirectionalStreetModel directionalStreetModel) {
        final Update update = new Update();
        update.push("outgoingStreetIds", directionalStreetModel.getDirectionalId());
        update.push("connectedStreetIds", directionalStreetModel.getDirectionalId());
        return update;
    }

    private Update createIntersectionToUpdateObject(final DirectionalStreetModel directionalStreetModel) {
        final Update update = new Update();
        update.push("incomingStreetIds", directionalStreetModel.getDirectionalId());
        update.push("connectedStreetIds", directionalStreetModel.getDirectionalId());
        return update;
    }

    @Override
    public void addStreet(final DirectionalStreetModel directionalStreetModel) {
        mongoOperations.save(directionalStreetModel);
        mongoOperations.updateMulti(
                createIntersectionFromUpdateQuery(directionalStreetModel),
                createIntersectionFromUpdateObject(directionalStreetModel),
                IntersectionModel.class
        );
        mongoOperations.updateMulti(
                createIntersectionToUpdateQuery(directionalStreetModel),
                createIntersectionToUpdateObject(directionalStreetModel),
                IntersectionModel.class
        );
    }

    @Override
    public void addIntersection(final IntersectionModel intersectionModel) {
        mongoOperations.save(intersectionModel);
    }

    @Override
    public ExtendedIntersectionModel getIntersectionDescription(final String externalId) {
        // NOTE: $geoNear and $near are not allowed inside aggregation queries, so we need to do this separately
        final Aggregation aggregationQuery = Aggregation.newAggregation(
                new MatchOperation(Criteria.where("externalId").is(externalId)),
                LookupOperation.newLookup()
                        .from("streets")
                        .localField("connectedStreetIds")
                        .foreignField("directionalId")
                        .as("connectedStreets")
        );
        return mongoOperations.aggregate(
                aggregationQuery,
                IntersectionModel.class,
                ExtendedIntersectionModel.class
        ).getMappedResults().get(0);
    }

    protected List<GeoLocation> removeDuplicates(List<GeoLocation> geoLocationList) {
        final List<GeoLocation> resultList = new ArrayList<>();
        final Set<String> seenNames = new HashSet<>();
        final Set<String> seenIds = new HashSet<>();
        geoLocationList.forEach(geoLocation -> {
            if (!seenIds.contains(geoLocation.getExternalId())
                    && (
                    !(geoLocation instanceof DirectionalStreetModel)
                            || !seenNames.contains(((DirectionalStreetModel) geoLocation).getName()))
            ) {
                seenIds.add(geoLocation.getExternalId());
                resultList.add(geoLocation);
                if (geoLocation instanceof DirectionalStreetModel) {
                    seenNames.add(((DirectionalStreetModel) geoLocation).getName());
                }
            }
        });
        return resultList;
    }

    @Override
    public List<GeoLocation> findNearestPoints(final Point location, int maxMatches) {
        final List<GeoLocation> geoLocationList = new ArrayList<>();
        final Query closestQuery = Query.query(
                Criteria.where("location").near(location)
        ).limit(2 * maxMatches);

        geoLocationList.addAll(mongoOperations.find(closestQuery, DirectionalStreetModel.class));
        geoLocationList.addAll(mongoOperations.find(closestQuery, IntersectionModel.class));

        return removeDuplicates(geoLocationList);
    }

    @Override
    public String getAdjacentNodeId(final String streetId) {
        final DirectionalStreetModel street = mongoOperations.findOne(
                Query.query(Criteria.where("directionalId").is(streetId)),
                DirectionalStreetModel.class
        );

        if (nonNull(street)) {
            return street.getSourceIntersectionId();
        }

        return StringUtils.EMPTY;
    }
}
