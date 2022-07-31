package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.GeoPointReverseConverter;
import com.example.accidentsRS.data.GeoPointData;
import com.example.accidentsRS.model.GeoLocation;
import org.springframework.stereotype.Component;

@Component
public class BaseGeoPointReverseConverter implements GeoPointReverseConverter {

    @Override
    public GeoPointData convert(final GeoLocation geoLocation) {
        GeoPointData geoPointData = new GeoPointData();
        geoPointData.setExternalId(geoLocation.getExternalId());
        geoPointData.setLocation(geoLocation.getLocation());
        return geoPointData;
    }
}
