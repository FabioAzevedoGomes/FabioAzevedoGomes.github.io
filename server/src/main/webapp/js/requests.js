
const filterAttributeNames = new Map();
filterAttributeNames.set('external-id', 'externalId');
filterAttributeNames.set('type', 'type');
filterAttributeNames.set('vehicle', 'involvedEntities');
filterAttributeNames.set('light-injuries', 'fatality.light_injuries');
filterAttributeNames.set('serious-injuries', 'fatality.serious_injuries');
filterAttributeNames.set('deaths', 'fatality.deaths');
filterAttributeNames.set('date', 'date.date');
filterAttributeNames.set('time', 'date.time_of_day');
filterAttributeNames.set('visibility', 'climate.visibility');
filterAttributeNames.set('precipitation', 'climate.precipitation_mm');
filterAttributeNames.set('air-temperature', 'climate.air_temp_celsius');
filterAttributeNames.set('wind-speed', 'climate.wind_speed_ms');
filterAttributeNames.set('relative-humidity', 'climate.relative_humidity_percentage');
filterAttributeNames.set('street', 'address.street1');
filterAttributeNames.set('longitude', 'address.location.longitude');
filterAttributeNames.set('latitude', 'address.location.latitude');
filterAttributeNames.set('region', 'address.region');

function isNumber(input_string) {
    return !isNaN(parseFloat(input_string)) && isFinite(input_string);
}

function readFilters(type) {
    var divs = document.getElementsByTagName("div");
    filterDivs = []
    for (var i = 0, len = divs.length; i < len; i++) {
        item = divs[i];
        if (item.id && item.id != `${type}-filter-id-0` && item.id.indexOf(`${type}-filter-id-`) == 0) {
            let value = `"value":"${item.children[3].innerHTML.trim()}"`;
            if (isNumber(item.children[3].innerHTML.trim())) {
                value = `"value":${item.children[3].innerHTML.trim()}`;
            }
            var stringValue = `
            {
                "fields": [ "${item.children[1].innerHTML.trim()}" ],
                "operator":"${item.children[2].innerHTML.trim().replace(' ', '_')}",
                ${value}
            }`;
            stringValue = stringValue.replaceAll(/(\r\n|\n|\r)/gm, "");
            filterDivs.push(JSON.parse(stringValue))
        }
    }
    return filterDivs;
}

function readAccidentFilters() {
    let accidentFilters = readFilters('accident');

    for (filter in accidentFilters) {
        accidentFilters[filter].fields[0] = filterAttributeNames.get(accidentFilters[filter].fields[0]);
    }

    return accidentFilters;
}

function readTimeFilters() {
    let timeFilters = readFilters('time');

    for (filter in timeFilters) {
        timeFilters[filter].fields[0] = filterAttributeNames.get(timeFilters[filter].fields[0]);
        if (timeFilters[filter].fields[0] == 'date.time_of_day') {
            if (timeFilters[filter].value.includes("AM")) {
                timeFilters[filter].value = timeFilters[filter].value.replace("AM", "").trim();
            } else if (timeFilters[filter].value.includes("PM")) {
                timeFilters[filter].value = timeFilters[filter].value.replace("PM", "").trim();
                timeFilters[filter].value = moment.utc(timeFilters[filter].value, 'hh:mm').add(12, 'hour').format('HH:mm');
            }
            timeFilters[filter].value = moment.utc(timeFilters[filter].value, 'hh:mm').hour() * 3600000;
        }
    }

    return timeFilters;
}

function readClimateFilters() {
    let climateFilters = readFilters('climate');

    for (filter in climateFilters) {
        climateFilters[filter].fields[0] = filterAttributeNames.get(climateFilters[filter].fields[0]);
    }

    return climateFilters;
}

function readLocationFilters() {
    let locationFilters = readFilters('location');

    for (filter in locationFilters) {
        locationFilters[filter].fields[0] = filterAttributeNames.get(locationFilters[filter].fields[0]);
        if (locationFilters[filter].fields[0] == 'address.street1') {
            locationFilters[filter].fields.push('address.street2');
        }
    }

    return locationFilters;
}

function prepareFilters() {
    var filters = []

    filters = filters.concat(readAccidentFilters())
    filters = filters.concat(readTimeFilters())
    filters = filters.concat(readClimateFilters())
    filters = filters.concat(readLocationFilters())

    if (filters) {
        return JSON.stringify(filters);
    } else {
        return `"[]"`;
    }
}

function getAccidents() {
    return $.ajax('/accidents/get', {
        contentType: 'application/json',
        type: 'POST',
        data: prepareFilters(),
        error: function (result) {
            alert('Error getting map data from backend');
        }
    });
}

function getPointSuggestions(longitude, latitude) {
    var location = { "longitude": longitude, "latitude": latitude };
    return $.ajax('/map/point/get', {
        contentType: 'application/json',
        type: 'POST',
        data: JSON.stringify(location),
        error: function (result) {
            alert('Error getting map data from backend');
        }
    });
}