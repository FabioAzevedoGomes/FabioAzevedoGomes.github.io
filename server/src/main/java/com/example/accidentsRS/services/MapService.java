package com.example.accidentsRS.services;

import com.example.accidentsRS.model.DirectionalStreetModel;
import com.example.accidentsRS.model.IntersectionModel;

public interface MapService {

    void addStreet(DirectionalStreetModel directionalStreetModel);

    void addIntersection(IntersectionModel intersectionModel);
}
