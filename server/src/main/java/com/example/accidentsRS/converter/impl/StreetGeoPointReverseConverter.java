package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.data.GeoPointData;
import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.GeoLocation;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StreetGeoPointReverseConverter extends BaseGeoPointReverseConverter {

    @Override
    public GeoPointData convert(final GeoLocation geoLocation) {
        GeoPointData geoPointData = super.convert(geoLocation);
        if (Objects.nonNull(geoPointData)) {
            geoPointData.setExternalId(((DirectionalStreetModel) geoLocation).getDirectionalId());
            final String streetName = ((DirectionalStreetModel) geoLocation).getName();
            geoPointData.setName(StringUtils.isBlank(streetName) ? "unknown street" : streetName);
            geoPointData.setKind("Street");
        }
        return geoPointData;
    }
}
