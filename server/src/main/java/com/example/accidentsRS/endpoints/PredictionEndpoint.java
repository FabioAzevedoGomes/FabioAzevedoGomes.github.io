package com.example.accidentsRS.endpoints;

import com.example.accidentsRS.data.RegionRiskData;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.facade.PredictionFacade;
import com.example.accidentsRS.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/model/persist", method = RequestMethod.POST)
    public void persistTrainedModel(@RequestBody final MultipartFile predictiveModel) throws PersistenceException {
        defaultPredictionFacade.savePredictiveModel(predictiveModel);
    }

    @RequestMapping(value = "/model/get", method = RequestMethod.GET)
    public ResponseEntity<Resource> getTrainedModelModel(@RequestParam("name") final String modelName) {
        ByteArrayResource resource = new ByteArrayResource(defaultPredictionFacade.getPredictiveModel(modelName).getData());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("model.hd5")
                                .build().toString())
                .body(resource);
    }

    @RequestMapping(value = "/model/predict", method = RequestMethod.POST)
    public float predict(@RequestBody final Location location) {
        return defaultPredictionFacade.predict(location);
    }

    @RequestMapping(value = "/get/forecast", method = RequestMethod.GET)
    public List<RegionRiskData> getForecast() {
        return defaultPredictionFacade.getForecast();
    }
}
