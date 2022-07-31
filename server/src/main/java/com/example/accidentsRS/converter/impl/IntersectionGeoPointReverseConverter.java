package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.data.GeoPointData;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.ExtendedIntersectionModel;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.services.MapService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class IntersectionGeoPointReverseConverter extends BaseGeoPointReverseConverter {

    private static final String DEFAULT_INTERSECTION_NAME = "Intersection between ";

    @Autowired
    MapService defaultMapService;

    @Override
    public GeoPointData convert(final GeoLocation geoLocation) {
        final GeoPointData geoPointData = super.convert(geoLocation);
        final ExtendedIntersectionModel expandedIntersections =
                defaultMapService.getIntersectionDescription(geoLocation.getExternalId());

        final List<DirectionalStreetModel> connectedStreets =
                expandedIntersections.getConnectedStreets();

        if (!CollectionUtils.isEmpty(connectedStreets) && Objects.nonNull(geoPointData)) {
            String streets = String.join(", ", connectedStreets.stream()
                    .map(DirectionalStreetModel::getName)
                    .map(street -> StringUtils.isBlank(street) ? "unknown street" : street)
                    .collect(Collectors.toSet()));
            if (StringUtils.isBlank(streets)) {
                streets = "unknown streets";
            } else if (streets.split(",").length == 1) {
                streets += ", unknown street";
            }
            geoPointData.setName(DEFAULT_INTERSECTION_NAME + streets);
            geoPointData.setKind("Intersection");
        }
        return geoPointData;
    }
}
