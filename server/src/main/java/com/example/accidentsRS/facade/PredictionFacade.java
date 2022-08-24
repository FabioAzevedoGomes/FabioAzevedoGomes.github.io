package com.example.accidentsRS.facade;

import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.prediction.Region;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PredictionFacade {
    void savePredictiveModel(MultipartFile predictiveModel, String regionListJson) throws PersistenceException;
    List<Region> forecastTodayUsing(String modelName);
    List<Region> forecastToday();
    float predictForPointUsing(Location point, String modelName);
    float predictForPoint(Location point);
}
