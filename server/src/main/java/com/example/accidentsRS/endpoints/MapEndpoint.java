package com.example.accidentsRS.endpoints;

import com.example.accidentsRS.data.DirectionalStreetData;
import com.example.accidentsRS.data.GeoPointData;
import com.example.accidentsRS.data.IntersectionData;
import com.example.accidentsRS.data.PathSuggestionParameterWrapper;
import com.example.accidentsRS.exceptions.PersistenceException;
import com.example.accidentsRS.facade.MapFacade;
import com.example.accidentsRS.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/map")
public class MapEndpoint extends AbstractEndpoint {

    private static final Logger LOGGER = Logger.getLogger(MapEndpoint.class.getName());

    @Autowired
    MapFacade defaultMapFacade;

    @RequestMapping(value = "/intersection/create", method = RequestMethod.POST)
    public void createIntersection(@RequestBody final IntersectionData intersectionData) throws PersistenceException {
        defaultMapFacade.addIntersection(intersectionData);
    }

    @RequestMapping(value = "/street/create", method = RequestMethod.POST)
    public void createStreet(@RequestBody final DirectionalStreetData streetData) throws PersistenceException {
        defaultMapFacade.addStreet(streetData);
    }

    @RequestMapping(value = "/point/get", method = RequestMethod.POST)
    public List<GeoPointData> getPointDisambiguation(@RequestBody final Location location) {
        return defaultMapFacade.findNearestPoints(location);
    }

    @RequestMapping(value = "/path/suggest", method = RequestMethod.POST)
    public List<Location> suggestPath(@RequestBody final PathSuggestionParameterWrapper pathSuggestionParameters) {
        return defaultMapFacade.suggestPath(pathSuggestionParameters);
    }
}
