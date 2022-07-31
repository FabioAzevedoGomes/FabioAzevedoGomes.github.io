package com.example.accidentsRS.factory;

import com.example.accidentsRS.converter.GeoPointReverseConverter;
import com.example.accidentsRS.model.GeoLocation;

public interface GeoPointConverterFactory {
    GeoPointReverseConverter getConverterForGeoLocation(GeoLocation geoLocation);
}
