package com.example.accidentsRS.endpoints;

import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.facade.PredictionFacade;
import com.example.accidentsRS.model.Location;
import com.example.accidentsRS.model.prediction.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/prediction")
public class PredictionEndpoint {

    private static final Logger LOGGER = Logger.getLogger(PredictionEndpoint.class.getName());

    @Autowired
    PredictionFacade defaultPredictionFacade;

    @RequestMapping(value = "/model/persist", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void persistTrainedModel(@RequestPart("bounds") final String bounds,
                                    @RequestPart("predictiveModel") final MultipartFile predictiveModel) throws PersistenceException {
        defaultPredictionFacade.savePredictiveModel(predictiveModel, bounds);
    }

    @RequestMapping(value = "/model/predict/today", method = RequestMethod.GET)
    public List<Region> predictToday() {
        return defaultPredictionFacade.forecastToday();
    }

    @RequestMapping(value = "/model/predict/with", method = RequestMethod.POST)
    public List<Region> predictToday(@RequestBody final String model) {
        return defaultPredictionFacade.forecastTodayUsing(model);
    }

    @RequestMapping(value = "/model/predict/point", method = RequestMethod.POST)
    public float predictPoint(@RequestBody final Location location) {
        return defaultPredictionFacade.predictForPoint(location);
    }

    @RequestMapping(value = "model/get/predictors", method = RequestMethod.GET)
    public List<String> getAvailablePredictors() {
        return defaultPredictionFacade.getPredictorNames();
    }
}
