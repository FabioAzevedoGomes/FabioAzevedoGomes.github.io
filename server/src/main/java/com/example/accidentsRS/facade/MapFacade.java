package com.example.accidentsRS.facade;

import com.example.accidentsRS.data.DirectionalStreetData;
import com.example.accidentsRS.data.IntersectionData;

public interface MapFacade {

    void addStreet(DirectionalStreetData directionalStreetData);

    void addIntersection(IntersectionData intersectionData);
}
