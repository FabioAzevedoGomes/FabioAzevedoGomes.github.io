package com.example.accidentsRS.facade.impl;

import com.example.accidentsRS.data.InboundPredictor;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.facade.PredictionFacade;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.prediction.Predictor;
import com.example.accidentsRS.model.prediction.Region;
import com.example.accidentsRS.services.PredictionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class DefaultPredictionFacade implements PredictionFacade {

    private static final Logger LOGGER = Logger.getLogger(DefaultPredictionFacade.class.getName());

    @Autowired
    PredictionService defaultPredictionService;

    @Value("${default.model.name}")
    private String defaultModel;

    private String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void savePredictiveModel(final MultipartFile predictiveModel, final String regionListJson) throws PersistenceException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Region[] regionArray = mapper.readValue(regionListJson, Region[].class);
            Arrays.asList(regionArray).forEach(region -> {
                region.setRegionId(getUniqueId());
                region.setPredictor(predictiveModel.getOriginalFilename());
            });
            final List<Region> regionList = Arrays.asList(regionArray);

            final Predictor predictor = new Predictor();
            predictor.setName(predictiveModel.getOriginalFilename());
            predictor.setModel(new Binary(BsonBinarySubType.BINARY, predictiveModel.getBytes()));
            predictor.setDomain(regionList.stream().map(Region::getRegionId).collect(Collectors.toList()));
            defaultPredictionService.savePredictor(predictor, regionList);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
            throw new PersistenceException("Invalid region list");
        } catch (final IOException ioException) {
            LOGGER.log(Level.SEVERE, "Error saving predictive model " + predictiveModel.getOriginalFilename() + " not persisted!", ioException);
            throw new PersistenceException("Error saving model to database!");
        }
    }

    @Override
    public List<Region> forecastTodayUsing(final String modelName) {
        return defaultPredictionService.forecastTodayUsing(modelName);
    }

    @Override
    public List<Region> forecastToday() {
        return forecastTodayUsing(getDefaultModel());
    }

    @Override
    public float predictForPointUsing(final Location point, final String modelName) {
        return defaultPredictionService.predictRiskAtPointUsing(point, modelName);
    }

    @Override
    public float predictForPoint(Location point) {
        return predictForPointUsing(point, getDefaultModel());
    }

    @Override
    public List<String> getPredictorNames() {
        return defaultPredictionService.getPredictorNames();
    }

    public String getDefaultModel() {
        return defaultModel;
    }

    public void setDefaultModel(String defaultModel) {
        this.defaultModel = defaultModel;
    }
}
