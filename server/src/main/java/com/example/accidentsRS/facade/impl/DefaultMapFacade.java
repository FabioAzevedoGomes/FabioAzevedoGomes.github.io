package com.example.accidentsRS.facade.impl;

import com.example.accidentsRS.converter.DirectionalStreetConverter;
import com.example.accidentsRS.converter.IntersectionConverter;
import com.example.accidentsRS.data.DirectionalStreetData;
import com.example.accidentsRS.data.IntersectionData;
import com.example.accidentsRS.facade.MapFacade;
import com.example.accidentsRS.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultMapFacade implements MapFacade {

    @Autowired
    IntersectionConverter defaultIntersectionConverter;

    @Autowired
    DirectionalStreetConverter defaultDirectionalStreetConverter;

    @Autowired
    MapService defaultMapService;

    @Override
    public void addStreet(final DirectionalStreetData directionalStreetData) {
        defaultMapService.addStreet(
                defaultDirectionalStreetConverter.convert(directionalStreetData)
        );
    }

    @Override
    public void addIntersection(final IntersectionData intersectionData) {
        defaultMapService.addIntersection(
                defaultIntersectionConverter.convert(intersectionData)
        );
    }
}
