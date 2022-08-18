package com.example.accidentsRS.services.impl;

import com.example.accidentsRS.dao.IntersectionDao;
import com.example.accidentsRS.dao.StreetDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.ExtendedIntersectionModel;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.model.IntersectionModel;
import com.example.accidentsRS.services.MapService;
import com.example.accidentsRS.services.factory.FilterFactory;
import com.example.accidentsRS.services.factory.UpdateFactory;
import com.example.accidentsRS.services.util.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultMapService implements MapService {

    private static final Logger LOGGER = Logger.getLogger(DefaultMapService.class.getName());

    @Autowired
    FilterFactory defaultFilterFactory;

    @Autowired
    UpdateFactory defaultUpdateFactory;

    @Autowired
    StreetDao defaultStreetDao;

    @Autowired
    IntersectionDao defaultIntersectionDao;

    protected void updateIntersectionAtStartOf(final DirectionalStreetModel directionalStreetModel) throws PersistenceException {
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put(IntersectionModel.OUTGOING_STREET_IDS, directionalStreetModel.getDirectionalId());
        updateFields.put(IntersectionModel.CONNECTED_STREET_IDS, directionalStreetModel.getDirectionalId());
        defaultIntersectionDao.update(
                defaultFilterFactory.createFiltersForFindingIntersectionId(directionalStreetModel.getSourceIntersectionId()),
                defaultUpdateFactory.createPushUpdatesForFieldValuePairs(updateFields)
        );
    }

    protected void updateIntersectionAtEndOf(final DirectionalStreetModel directionalStreetModel) throws PersistenceException {
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put(IntersectionModel.INCOMING_STREET_IDS, directionalStreetModel.getDirectionalId());
        updateFields.put(IntersectionModel.CONNECTED_STREET_IDS, directionalStreetModel.getDirectionalId());
        defaultIntersectionDao.update(
                defaultFilterFactory.createFiltersForFindingIntersectionId(directionalStreetModel.getSourceIntersectionId()),
                defaultUpdateFactory.createPushUpdatesForFieldValuePairs(updateFields)
        );
    }

    @Override
    public void addStreet(final DirectionalStreetModel directionalStreetModel) {
        try {
            defaultStreetDao.save(directionalStreetModel);
            updateIntersectionAtStartOf(directionalStreetModel);
            updateIntersectionAtEndOf(directionalStreetModel);
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void addIntersection(final IntersectionModel intersectionModel) {
        try {
            defaultIntersectionDao.save(intersectionModel);
        } catch (final PersistenceException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public ExtendedIntersectionModel getIntersectionDescription(final String externalId) {
        return defaultIntersectionDao.getIntersectionDescription(externalId);
    }

    @Override
    public List<GeoLocation> findNearestPoints(final Point location, int maxMatches) {
        final List<GeoLocation> geoLocationList = new ArrayList<>();
        geoLocationList.addAll(defaultStreetDao.getNearbyStreets(location, maxMatches));
        geoLocationList.addAll(defaultIntersectionDao.getNearbyIntersections(location, maxMatches));
        return MapUtils.removeDuplicateLocations(geoLocationList);
    }

    @Override
    public String getAdjacentNodeId(final String streetId) {
        final List<DirectionalStreetModel> street = defaultStreetDao.get(
                defaultFilterFactory.createFilterForFieldValuePairs(
                        Collections.singletonMap(DirectionalStreetModel.DIRECTIONAL_ID, streetId)
                )
        );

        if (!CollectionUtils.isEmpty(street)) {
            return street.get(0).getSourceIntersectionId();
        }

        return StringUtils.EMPTY;
    }
}
