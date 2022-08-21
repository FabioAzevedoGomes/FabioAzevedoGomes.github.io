package com.example.accidentsRS.dao.impl;

import com.example.accidentsRS.dao.PredictiveModelDao;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.PredictiveModel;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultPredictiveModelDao implements PredictiveModelDao {

    private static final Logger LOGGER = Logger.getLogger(DefaultPredictiveModelDao.class.getName());

    @Autowired
    MongoOperations mongoOperations;

    @Value("${temp.model.file.name}")
    private String tempFilename;

    protected File getTempFile(final byte[] modelBytes) throws PersistenceException {
        final File modelDir = new ApplicationTemp().getDir();
        try {
            File modelFile = new File(modelDir, tempFilename + ".h5");
            if (modelFile.exists()) {
                return modelFile;
            } else if (modelFile.createNewFile()) {
                LOGGER.log(Level.INFO, "Created new file at " + modelFile.getAbsolutePath());
                FileOutputStream outputStream = new FileOutputStream(modelFile);
                outputStream.write(modelBytes);
                return new File(modelFile.getAbsolutePath());
            }
        } catch (final IOException e) {
            // Nothing
        }

        throw new PersistenceException("Cannot get or create model file!");
    }

    @Override
    public PredictiveModel getPredictionModel(final String modelName) {
        return mongoOperations.findOne(
                Query.query(Criteria.where(PredictiveModel.VERSION).is(modelName)),
                PredictiveModel.class
        );
    }

    @Override
    public ComputationGraph getNetworkByName(final String modelName) throws PersistenceException, IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        final PredictiveModel predictiveModel = getPredictionModel(modelName);
        final InputStream modelByteStream = new ByteArrayInputStream(predictiveModel.getModel().getData());
        try {
            return KerasModelImport.importKerasModelAndWeights(modelByteStream);
        } catch (UnsupportedOperationException e) {
            LOGGER.log(Level.WARNING, "Could not import model from input stream, trying file approach");
            final String modelTempFile = getTempFile(predictiveModel.getModel().getData()).getAbsolutePath();
            return KerasModelImport.importKerasModelAndWeights(modelTempFile, false);
        }
    }
}
