package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.DirectionalStreetConverter;
import com.example.accidentsRS.data.DirectionalStreetData;
import com.example.accidentsRS.model.DirectionalStreetModel;
import org.springframework.stereotype.Component;

@Component
public class DefaultDirectionalStreetConverter implements DirectionalStreetConverter {

    @Override
    public DirectionalStreetModel convert(DirectionalStreetData directionalStreetData) {
        DirectionalStreetModel directionalStreetModel = new DirectionalStreetModel();
        directionalStreetModel.setExternalId(directionalStreetData.getExternalId());
        directionalStreetModel.setDirectionalId(directionalStreetData.getDirectionalId());
        directionalStreetModel.setLocation(directionalStreetData.getLocation());
        directionalStreetModel.setDestinationIntersectionId(directionalStreetData.getDestination_intersection_id());
        directionalStreetModel.setSourceIntersectionId(directionalStreetData.getSource_intersection_id());
        directionalStreetModel.setName(directionalStreetData.getName());
        directionalStreetModel.setLength(directionalStreetData.getLength());
        return directionalStreetModel;
    }

}
