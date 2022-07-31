package com.example.accidentsRS.factory.impl;

import com.example.accidentsRS.converter.GeoPointReverseConverter;
import com.example.accidentsRS.factory.GeoPointConverterFactory;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.GeoLocation;
import com.example.accidentsRS.model.IntersectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultGeoPointConverterFactory implements GeoPointConverterFactory {

    @Autowired
    GeoPointReverseConverter baseGeoPointReverseConverter;

    @Autowired
    GeoPointReverseConverter intersectionGeoPointReverseConverter;

    @Autowired
    GeoPointReverseConverter streetGeoPointReverseConverter;

    @Override
    public GeoPointReverseConverter getConverterForGeoLocation(final GeoLocation geoLocation) {
        GeoPointReverseConverter appropriateConverter = baseGeoPointReverseConverter;
        if (geoLocation instanceof DirectionalStreetModel) {
            appropriateConverter = streetGeoPointReverseConverter;
        } else if (geoLocation instanceof IntersectionModel) {
            appropriateConverter = intersectionGeoPointReverseConverter;
        }
        return appropriateConverter;
    }
}
