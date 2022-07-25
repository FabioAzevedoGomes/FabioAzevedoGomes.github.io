package com.example.accidentsRS.endpoints;

import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.AccidentFacade;
import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.services.ValueSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/accidents")
public class AccidentEndpoint extends AbstractEndpoint {

    private static final Logger LOGGER = Logger.getLogger(AccidentEndpoint.class.getName());

    @Autowired
    AccidentFacade defaultAccidentFacade;

    @Autowired
    ValueSearchService valueSearchService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody final AccidentData accidentData) throws PersistenceException {
        defaultAccidentFacade.createAccidentRecord(accidentData);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST) // Using post here to support the filters
    public List<AccidentData> getSome(@RequestBody(required = false) final List<FilterWrapperData> getFilters) throws ValidationException {
        if (Objects.isNull(getFilters)) {
            return defaultAccidentFacade.findAllMatchingFilter(new ArrayList<>());
        } else {
            return defaultAccidentFacade.findAllMatchingFilter(getFilters);
        }
    }

    // TODO UPDATE + DELETE endpoints

    @RequestMapping(value = "/get/types", method = RequestMethod.GET)
    public List<String> getTypes() {
        return valueSearchService.getAllAccidentTypes();
    }

    @RequestMapping(value = "/get/vehicles", method = RequestMethod.GET)
    public List<String> getVehicles() {
        return valueSearchService.getAllVehicleTypes();
    }

    @RequestMapping(value = "/get/regions", method = RequestMethod.GET)
    public List<String> getRegions() {
        return valueSearchService.getAllRegions();
    }

}
