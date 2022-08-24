package com.example.accidentsRS.model.prediction;

import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.model.Location;
import org.bson.types.Binary;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.boot.system.ApplicationTemp;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AggregatePredictorModel {

    private static final Logger LOGGER = Logger.getLogger(AggregatePredictorModel.class.getName());

    public static final String NAME = "name";
    private String name;
    public static final String MODEL = "model";
    private Binary model;
    public static final String MODEL_COMPILED = "modelCompiled";
    private ComputationGraph modelCompiled;
    public static final String POPULATED_DOMAIN = "populatedDomain";
    private List<Region> populatedDomain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Binary getModel() {
        return model;
    }

    public void setModel(Binary model) {
        this.model = model;
    }

    public ComputationGraph getModelCompiled() {
        return modelCompiled;
    }

    public void setModelCompiled(ComputationGraph modelCompiled) {
        this.modelCompiled = modelCompiled;
    }

    public List<Region> getDomain() {
        return populatedDomain;
    }

    public void setDomain(List<Region> populatedDomain) {
        this.populatedDomain = populatedDomain;
    }

    private File getTempFile(final byte[] modelBytes) throws PersistenceException, IOException {
        final File modelDir = new ApplicationTemp().getDir();

        File modelFile = new File(modelDir, this.name);
        if (modelFile.exists()) {
            return modelFile;
        } else if (modelFile.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(modelFile);
            outputStream.write(modelBytes);
            return new File(modelFile.getAbsolutePath());
        }

        throw new PersistenceException("Cannot get or create model file!");
    }

    public float predict(final INDArray features, final Region region) {
        final Location center = region.getCenter();
        final INDArray secondFeatures = Nd4j.zeros(1, 2);
        secondFeatures.reshape(1, 2);
        final INDArray[] inputData = new INDArray[]{
                features,
                Nd4j.create(new float[]{center.getLatitude(), center.getLongitude()})
        };
        return modelCompiled.output(inputData)[0].getFloat(0);
    }

    public AggregatePredictorModel compile() throws PersistenceException, IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        final InputStream modelByteStream = new ByteArrayInputStream(this.model.getData());
        try {
            this.modelCompiled = KerasModelImport.importKerasModelAndWeights(modelByteStream);
        } catch (final UnsupportedOperationException e) {
            final String modelTempFile = getTempFile(model.getData()).getAbsolutePath();
            this.modelCompiled = KerasModelImport.importKerasModelAndWeights(modelTempFile, false);
        }
        return this;
    }
}
