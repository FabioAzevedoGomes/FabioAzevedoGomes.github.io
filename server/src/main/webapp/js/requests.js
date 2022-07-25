
const filterAttributeNames = new Map();
filterAttributeNames.set('accidents-id-filter','externalId');
filterAttributeNames.set('accidents-type-filter','type');
filterAttributeNames.set('accidents-vehicle-filter','involvedEntities');
filterAttributeNames.set('accidents-light-inj-filter','fatality.light_injuries');
filterAttributeNames.set('accidents-serious-inj-filter','fatality.serious_injuries');
filterAttributeNames.set('accidents-death-filter','fatality.deaths');
filterAttributeNames.set('time-date-filter','date.date');
filterAttributeNames.set('time-time-filter','date.time_of_day');
filterAttributeNames.set('climate-visibility-filter','climate.visibility');
filterAttributeNames.set('climate-precipitation-filter','climate.precipitation_mm');
filterAttributeNames.set('climate-air-temp-filter','climate.air_temp_celsius');
filterAttributeNames.set('climate-wind-speed-filter','climate.wind_speed_ms');
filterAttributeNames.set('climate-relative-humidity-filter','climate.relative_humidity_percentage');
filterAttributeNames.set('location-street-filter','address.street1');
filterAttributeNames.set('location-longitude-filter','address.location.longitude');
filterAttributeNames.set('location-latitude-filter','address.location.latitude');
filterAttributeNames.set('location-region-filter','address.region');

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
                timeFilters[filter].value = moment.utc(timeFilters[filter].value,'hh:mm').add(12,'hour').format('HH:mm');
            }
            timeFilters[filter].value = moment.utc(timeFilters[filter].value,'hh:mm').hour() * 3600000;
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

function refreshMap() {
    if(document.getElementById("showHideSwitch").checked) {
        getAccidents();
    }
}

function getAccidents() {
    filters = prepareFilters();
    $.ajax('/accidents/get', {
        contentType: 'application/json',
        type: 'POST',
        data: filters,
        success: function (result) {
            reloadMap(result);
        },
        error: function (result) {
            alert('Error getting map data from backend');
        }
    }).then(function () {
        return false;
    });
}