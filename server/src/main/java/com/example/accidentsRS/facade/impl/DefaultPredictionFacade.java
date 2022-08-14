package com.example.accidentsRS.facade.impl;

import com.example.accidentsRS.model.PredictiveModel;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.facade.PredictionFacade;
import com.example.accidentsRS.services.PredictionService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultPredictionFacade implements PredictionFacade {

    private static final Logger LOGGER = Logger.getLogger(DefaultPredictionFacade.class.getName());

    @Autowired
    PredictionService defaultPredictionService;

    @Override
    public void savePredictiveModel(final MultipartFile predictiveModel) throws PersistenceException {
        try {
            PredictiveModel model = new PredictiveModel();
            model.setVersion(predictiveModel.getOriginalFilename());
            model.setModel(new Binary(BsonBinarySubType.BINARY, predictiveModel.getBytes()));
            defaultPredictionService.savePredictionModel(model);
        } catch (final PersistenceException persistenceException) {
            LOGGER.log(Level.SEVERE, "Error saving predictive model " + predictiveModel.getName() + " not persisted!", persistenceException);
            throw persistenceException;
        } catch (final IOException ioException) {
            LOGGER.log(Level.SEVERE, "Error saving predictive model " + predictiveModel.getName() + " not persisted!", ioException);
            throw new PersistenceException("Error saving model to database!");
        }
    }

    @Override
    public Binary getPredictiveModel(final String modelName) {
        return defaultPredictionService.getPredictionModel(modelName).getModel();
    }
}
