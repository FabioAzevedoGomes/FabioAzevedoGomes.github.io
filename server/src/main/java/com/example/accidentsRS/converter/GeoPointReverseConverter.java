package com.example.accidentsRS.converter;

import com.example.accidentsRS.data.GeoPointData;
import com.example.accidentsRS.model.GeoLocation;
import org.springframework.core.convert.converter.Converter;

public interface GeoPointReverseConverter extends Converter<GeoLocation, GeoPointData> {
    GeoPointData convert(GeoLocation geoLocation);
}
