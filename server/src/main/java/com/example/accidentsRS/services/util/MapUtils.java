package com.example.accidentsRS.services.util;

import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.GeoLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapUtils {

    public static List<GeoLocation> removeDuplicateLocations(final List<GeoLocation> geoLocationList) {
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
}
