package com.example.accidentsRS.endpoints;

import com.example.accidentsRS.data.AccidentData;
import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.endpoints.data.UpdateWrapper;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.AccidentFacade;
import com.example.accidentsRS.services.impl.DefaultValueSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/accidents")
public class AccidentEndpoint {

    private static final Logger LOGGER = Logger.getLogger(AccidentEndpoint.class.getName());

    @Autowired
    AccidentFacade defaultAccidentFacade;

    @Autowired
    DefaultValueSearchService defaultValueSearchService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody final AccidentData accidentData) throws PersistenceException {
        defaultAccidentFacade.createAccidentRecord(accidentData);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public List<AccidentData> get(@RequestBody(required = false) final List<FilterWrapperData> filters) throws ValidationException {
        return defaultAccidentFacade.findAllMatchingFilter(filters);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody final UpdateWrapper updateWrapper) throws ValidationException {
        defaultAccidentFacade.updateAllMatchingFilter(updateWrapper);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestBody final List<FilterWrapperData> filters) throws ValidationException {
        defaultAccidentFacade.deleteAllMatchingFilter(filters);
    }

    @RequestMapping(value = "/get/types", method = RequestMethod.GET)
    public List<String> getTypes() {
        return defaultValueSearchService.getAllAccidentTypes();
    }

    @RequestMapping(value = "/get/vehicles", method = RequestMethod.GET)
    public List<String> getVehicles() {
        return defaultValueSearchService.getAllVehicleTypes();
    }

    @RequestMapping(value = "/get/regions", method = RequestMethod.GET)
    public List<String> getRegions() {
        return defaultValueSearchService.getAllRegions();
    }

    @RequestMapping(value = "/get/dates", method = RequestMethod.GET)
    public List<java.util.Date> getDates() {
        return defaultValueSearchService.getAllDates();
    }
}
