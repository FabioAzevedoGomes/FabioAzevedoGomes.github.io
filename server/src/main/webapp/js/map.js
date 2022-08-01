let isChoosingLocation = true; // TODO Change this later

const LayerNames = {
    Accident: "Accident",
    LocationSuggestion: "LocationSuggestion"
}

class BaseLayer {

    constructor(name) {
        this.features = [];
        this.name = name;
        this.active = false;
        this.sourceVector = new ol.source.Vector({ features: this.features });
        this.layerVector = new ol.layer.Vector({
            source: this.sourceVector,
            name: this.name
        });
    }

    reload() {
        this.sourceVector = new ol.source.Vector({ features: this.features });
        this.layerVector = new ol.layer.Vector({
            source: this.sourceVector,
            name: this.name
        });
    }

    makePointFeature(featureData, location) {
        return new ol.Feature({
            attributes: { "data": featureData },
            geometry: new ol.geom.Point(ol.proj.fromLonLat([location.longitude, location.latitude]))
        });
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
            displayAccidentDetails(feature.j.attributes['data']);
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

    constructor() {
        super(`LocationSuggestion`);
    }

    processClick(evt) {
        if (isChoosingLocation) {
            var lonLatArray = ol.proj.toLonLat(evt.coordinate);
            getPointSuggestions(
                lonLatArray[0],
                lonLatArray[1]
            ).then(points => {
                points.forEach(point => {
                    let location = point['location'];
                    if (location.longitude && location.latitude) {
                        this.features.push(this.makePointFeature(point, location));
                    }
                });
            }).then(() => globalMap.enableLayer(this.LAYER_NAME));
        }
    }

    refresh(thenFunction) {
        // noop
    }
}

class MyMap {

    DEFAULT_LOCATION = [-51.21, -30.04]

    constructor() {
        this.layers = [
            new AccidentsLayer(),
            new LocationSuggestionLayer(),
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
}

var globalMap = new MyMap();
globalMap.initClickHandlers();
