const raster = new ol.layer.Tile({
    source: new ol.source.OSM(),
});

let features = [];
let source = new ol.source.Vector({ features });

let vector = new ol.layer.Vector({
    source: source,
});

var map = new ol.Map({
    target: 'map',
    layers: [raster, vector],
    view: new ol.View({
        center: ol.proj.fromLonLat([-51.21, -30.04]),
        zoom: 13
    })
});

function reloadMap(accidents) {
    features = [];
    clearMap();
    document.getElementById('match-quantity').innerHTML = accidents.length;
    if (accidents.length > 0) {
        for (i = 0; i < accidents.length; i++) {
            let item = accidents[i].address.location;
            if (item.longitude != null && item.latitude != null) {
                let feature = new ol.Feature({
                    attributes: {"accident" : accidents[i]},
                    geometry: new ol.geom.Point(ol.proj.fromLonLat([item.longitude, item.latitude]))
                });
                features.push(feature);
            }
        }
        source = new ol.source.Vector({ features });
        vector = new ol.layer.Vector({
            name: "markers",
            source: source,
        });
        map.addLayer(vector);
    }
}

function clearMap() {
    map.getLayers().forEach(function (layer) {
        if (layer.get('name') == 'markers')
            map.removeLayer(layer);
    });
}

map.on('click', function(evt) {
    var feature = map.forEachFeatureAtPixel(evt.pixel,
      function(feature) {
        return feature;
      });
    if (feature) {
        displayDetailsPopup(feature.j.attributes.accident);
    }
});
