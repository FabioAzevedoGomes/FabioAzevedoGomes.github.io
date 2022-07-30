package com.example.accidentsRS.controllers;

import com.example.accidentsRS.services.impl.DefaultAccidentService;
import com.example.accidentsRS.services.impl.DefaultValueSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
public class HomePageController {

    private static final Logger LOG = Logger.getLogger(HomePageController.class.toString());

    @Autowired
    DefaultAccidentService accidentSearchService;

    @Autowired
    DefaultValueSearchService defaultValueSearchService;

    private static final String HOME_PAGE = "homepage";

    private static final String ALL_ACCIDENT_TYPES_LIST = "allAccidentsTypesList";
    private static final String ALL_VEHICLE_TYPES_LIST = "allVehicleTypesList";
    private static final String ALL_REGIONS_LIST = "allRegionsList";

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ModelAndView homePageGet() {
        ModelAndView modelAndView = new ModelAndView(HOME_PAGE);
        modelAndView.addObject(ALL_ACCIDENT_TYPES_LIST, defaultValueSearchService.getAllAccidentTypes());
        modelAndView.addObject(ALL_VEHICLE_TYPES_LIST, defaultValueSearchService.getAllVehicleTypes());
        modelAndView.addObject(ALL_REGIONS_LIST, defaultValueSearchService.getAllRegions());
        return modelAndView;
    }

}
