package com.example.accidentsRS.services.factory;

import com.example.accidentsRS.model.prediction.Region;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Date;

public interface FeatureFactory {
    INDArray getFeaturesForRegionAndDate(Region region, Date date);
}
