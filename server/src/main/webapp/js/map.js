const LayerNames = {
    Accident: "Accident",
    LocationSuggestion: "LocationSuggestion",
    PathViewing: "PathView",
    RiskHeatmap: "RiskHeatmap"
}

class BaseLayer {

    constructor(name) {
        this.features = [];
        this.name = name;
        this.active = false;
        this.sourceVector = new ol.source.Vector({ features: this.features });
        this.layerVector = new ol.layer.Vector({
            declutter: true,
            source: this.sourceVector,
            name: this.name
        });
    }

    reload() {
        this.sourceVector = new ol.source.Vector({ features: this.features });
        this.layerVector = new ol.layer.Vector({
            declutter: true,
            source: this.sourceVector,
            name: this.name
        });
    }

    makePointFeature(featureData, location) {
        let feature = new ol.Feature({
            attributes: { "data": featureData },
            geometry: new ol.geom.Point(ol.proj.fromLonLat([location.longitude, location.latitude])),
        });
        return feature;
    }

    getVectorLayer() {
        return this.layerVector;
    }

    getName() {
        return this.name;
    }

    setFeatures(features) {
        this.features = features;
    }

    enable() {
        this.active = true;
    }

    disable() {
        this.active = false;
    }

    isActive() {
        return this.active;
    }

    processClick(evt) { }

    refresh(thenFunction) { }
}

class AccidentsLayer extends BaseLayer {

    LAYER_NAME = `Accident`

    constructor() {
        super(`Accident`);
    }

    processClick(evt) {
        var feature = globalMap.map.forEachFeatureAtPixel(evt.pixel, function (feature) { return feature; });
        if (feature) {
            displayAccidentDetails(feature.A.attributes['data']);
        }
    }

    shouldRefresh() {
        return $(`#showHideSwitch`)[0].checked;
    }

    refresh(thenFunction) {
        if (this.shouldRefresh()) {
            getAccidents().then(accidents => {
                $(`#match-quantity`)[0].innerHTML = accidents.length;
                accidents.forEach(accident => {
                    let location = accident['address'].location;
                    if (location.longitude && location.latitude) {
                        this.features.push(this.makePointFeature(accident, location));
                    }
                });
                thenFunction();
            });
        }
    }
}

class LocationSuggestionLayer extends BaseLayer {

    LAYER_NAME = `LocationSuggestion`;
    States = {
        ChoosingInitialLocation: "ChoosingInitialLocation",
        ChoosingBetweenOptions: "ChoosingBetweenOptions",
        Idle: "Idle"
    }

    constructor() {
        super(`LocationSuggestion`);
        this.state = this.States.ChoosingInitialLocation;
    }

    handleChoosingInitialLocation(evt) {
        var lonLatArray = ol.proj.toLonLat(evt.coordinate);
        getPointSuggestions(
            lonLatArray[0],
            lonLatArray[1]
        ).then(points => {
            points.forEach(point => {
                let location = point['location'];
                if (location.longitude && location.latitude) {
                    let feature = this.makePointFeature(point, location);
                    feature.style = { strokeWidth: 5, strokeColor: '#ff0000' };
                    this.features.push(feature);
                }
            });
            globalMap.enableLayer(this.LAYER_NAME);
            this.state = this.States.ChoosingBetweenOptions;
            globalMap.centerOnLayer(this.LAYER_NAME);
            globalPathfinder.showStartPickModal();
        });
    }

    handleChoosingBetweenOptions(evt) {
        var feature = globalMap.map.forEachFeatureAtPixel(evt.pixel, function (feature) { return feature; });
        if (feature) {
            globalPathfinder.setLocation(feature.A.attributes.data);
        }
        this.state = this.States.Idle;
        globalMap.disableLayer(LayerNames.LocationSuggestion);
        pathFindingProcess();
    }

    processClick(evt) {
        if (this.state == this.States.ChoosingInitialLocation
            && !globalPathfinder.isComplete()) {
            this.handleChoosingInitialLocation(evt);
        } else if (this.state == this.States.ChoosingBetweenOptions) {
            this.handleChoosingBetweenOptions(evt);
        }
    }

    reload() {
        this.state = this.States.ChoosingInitialLocation;
        super.reload();
    }

    disable() {
        this.state = this.States.Idle;
    }

    refresh(thenFunction) {
        // noop
    }
}

class PathViewingLayer extends BaseLayer {
    
    LAYER_NAME = `PathView`;
    
    constructor() {
        super(`PathView`);
    }

    processClick(evt) {
        // noop
    }

    getSelectedPredictor() {
        return $("#predictor-for-risk")[0].selectedOptions[0].value
    }

    refresh(thenFunction) {
        getPathBetweenPoints(globalPathfinder.getPoints(), this.getSelectedPredictor())
          .then(points => {
            var projectedPoints = [];
            for (var i = 0; i < points.length; i++) {
                if (points[i].longitude && points[i].latitude) {
                    this.features.push(this.makePointFeature(null, points[i]));
                    if (i > 0) {
                        var lineDefinition = [
                            [ol.proj.fromLonLat([points[i].latitude, points[i].longitude])],
                            [ol.proj.fromLonLat([points[i-1].latitude, points[i-1].longitude])]
                        ];
                        // this.features.push(new ol.Feature({ // TODO Lines don't work, openlayers sucks
                        //     geometry: new ol.geom.LineString(lineDefinition),
                        // }));
                    }
                }
            }
        }).then(thenFunction);
    }
}

class RiskHeatmapLayer extends BaseLayer {

    LAYER_NAME = `RiskHeatmap`

    constructor() {
        super(`RiskHeatmap`);
    }

    processClick(evt) {
        // noop
    }

    shouldRefresh() {
        return $(`#showHideRiskSwitch`)[0].checked;
    }

    adjustCoordinates(coords) {
        let min0 = 100000;
        let min1 = 100000;
        let max0 = -100000;
        let max1 = -100000;
        coords.forEach(coord => {
            if (coord[0] < min0) {
                min0 = coord[0];
            }
            if (coord[0] > max0) {
                max0 = coord[0];
            }
            if (coord[1] < min1) {
                min1 = coord[1];
            }
            if (coord[1] > max1) {
                max1 = coord[1];
            }
        });
        return [
            [min0, min1],
            [min0, max1],
            [max0, max1],
            [max0, min1]
        ];
    }

    getSelectedPredictor() {
        return $("#predictor-for-risk")[0].selectedOptions[0].value
    }

    refresh(thenFunction) {
        if (this.shouldRefresh()) {
            getRiskMap(this.getSelectedPredictor()).then(regions => {
                let max = 1e-10
                let min = 1
                regions.forEach(region => {
                    if (region['risk'] > max) {
                        max = region['risk'];
                    }
                    if (region['risk'] < min) {
                        min = region['risk']
                    }
                });
                regions.forEach(region => {
                    let ordered = this.adjustCoordinates(region['bounds']['coordinates'])
                    let regionProjection = [];
                    ordered.forEach(coord => {
                        regionProjection.push(ol.proj.fromLonLat([coord[1], coord[0]]))
                    });
                    let intensity = (region['risk'] - min) / (max - min);

                    let myStyle = new ol.style.Style({
                       stroke : new ol.style.Stroke({
                        color : `rgba(255,0,0,${intensity})`,
                        width : 1    
                     }),
                       fill : new ol.style.Fill({
                        color: `rgba(255,0,0,${intensity / 2.0})`
                     })
                    });
                    
                    let feature = new ol.Feature({
                        geometry: new ol.geom.Polygon([regionProjection])
                    });
                    feature.setStyle(myStyle);
                    this.features.push(feature);
                });
                thenFunction();
            });
        }
    }
}

class MyMap {

    DEFAULT_LOCATION = [-51.21, -30.04]

    constructor() {
        this.layers = [
            new AccidentsLayer(),
            new LocationSuggestionLayer(),
            new PathViewingLayer(),
            new RiskHeatmapLayer(),
        ];

        this.map = new ol.Map({
            target: 'map',
            layers: [new ol.layer.Tile({ source: new ol.source.OSM() })],
            view: new ol.View({
                center: ol.proj.fromLonLat([-51.21, -30.04]),
                zoom: 13
            })
        });
    }

    initClickHandlers() {
        var layers = this.layers;
        this.map.on('click', function (evt) {
            layers
                .filter(layer => layer.isActive())
                .forEach(layer => layer.processClick(evt));
        });
    }

    clear() {
        this.layers.forEach(myLayer => {
            let layerToDelete = this.map.getLayers()
                .find(mapLayer => mapLayer.get('name') == myLayer.getName(0));
            if (layerToDelete) {
                this.map.removeLayer(layerToDelete);
                myLayer.disable();
            }
        });
    }

    reloadLayer(layerName) {
        this.disableLayer(layerName);

        this.layers
            .filter(layer => layer.getName() == layerName)
            .forEach(layer => layer.refresh(() => this.enableLayer(layerName)));;
    }

    disableLayer(layerName) {
        this.layers
            .filter(layer => layer.getName() == layerName)
            .forEach(layer => {
                this.map.removeLayer(layer.getVectorLayer());
                layer.disable();
                layer.setFeatures([]);
                layer.reload();
            });
        this.map.render();
    }

    enableLayer(layerName) {
        this.layers
            .filter(layer => layer.getName() == layerName)
            .forEach(layer => {
                layer.reload();
                this.map.addLayer(layer.getVectorLayer());
                layer.enable();
            });
        this.map.render();
    }

    centerOnLayer(layerName) {
        this.layers
            .filter(layer => layer.getName() == layerName)
            .forEach(layer => {
                if (layer.features && layer.features.length > 0) {
                    this.map.getView().fit(layer.getVectorLayer().getSource().getExtent(), {
                        size: this.map.getSize(),
                    });
                }
            });
    }
}

var globalMap = new MyMap();
globalMap.initClickHandlers();
