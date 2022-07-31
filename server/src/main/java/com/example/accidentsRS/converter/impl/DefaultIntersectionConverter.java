package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.IntersectionConverter;
import com.example.accidentsRS.data.IntersectionData;
import com.example.accidentsRS.model.IntersectionModel;
import org.springframework.stereotype.Component;

@Component
public class DefaultIntersectionConverter implements IntersectionConverter {

    @Override
    public IntersectionModel convert(IntersectionData intersectionData) {
        IntersectionModel intersectionModel = new IntersectionModel();
        intersectionModel.setExternalId(intersectionData.getExternalId());
        intersectionModel.setLocation(intersectionData.getLocation());
        intersectionModel.setConnectedStreetIds(intersectionData.getConnected_street_ids());
        intersectionModel.setOutgoingStreetIds(intersectionData.getOutgoing_street_ids());
        intersectionModel.setIncomingStreetIds(intersectionData.getIncoming_street_ids());
        return intersectionModel;
    }
}
