let displayedAccidentId = '';

function updateAccidentIdLabel(new_label) {

	accidentIdSlot = document.getElementById('accident-card-id-slot');
	if (accidentIdSlot) {
		accidentIdSlot.innerHTML = new_label;
	}
}

function toggleAccident() {
    var accidentCard = document.getElementById('accident-card-wrapper'); 
    if (accidentCard) {
        if (accidentCard.style.display == 'flex') {
            accidentCard.style.display = 'none';
            displayedAccidentId = ''
        } else if (accidentCard.style.display == 'none') {
            accidentCard.style.display = 'flex';
        }
    }
}

const weekdays = ["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday", "Sunday"];
const months = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ];

function getWeekday(index) {
    return weekdays[index];
}

function getMonth(index) {
    return months[index - 1];
}

function getDay(day) {
    let dayString = day.toString();
    let addition = 'th';

    if (dayString.slice(-1) == '1') {
        addition = 'st';
    } else if (dayString.slice(-1) == '2') {
        addition = 'nd';
    } else if (dayString.slice(-1) == '3') {
        addition = 'rd';
    }

    if (day == '11' || day == '12' || day == '13') {
        addition = 'th'
    }

    return `${dayString}${addition}`;
}

function writeOutDate(date, weekday) {
    let parts = date.split('T');
    let dateString = parts[0];
    let dateParts = dateString.split('-');
    //let parts = date.split('/');
    return `${getWeekday(weekday)}, ${getMonth(dateParts[1])} ${getDay(dateParts[2])}, ${dateParts[0]}`;
}

function capitalizeString(string) {
    return string.charAt(0).toUpperCase() + string.toLowerCase().slice(1);
}

function capitalizeSentence(sentence) {
    let words = sentence.split(' ');
    let fixedSentence = '';

    words.forEach((word) => {
        fixedSentence += capitalizeString(word) + ' ';
    });

    return fixedSentence;
}

function getHour(date) {
    return date.split('T')[1].split('.')[0]
}

function populateTitle(accident) {
    $(`#accident-card-description`)[0].innerHTML =
        `${capitalizeString(accident['type'].replaceAll('_', ' '))} on ${writeOutDate(accident['date_time']['date'], accident['date_time']['weekday'])}, at ${getHour(accident['date_time']['hour'])}`;
    $(`#accident-card-id`)[0].innerHTML = accident['externalId'];
}

function populateVehicleInfo(entities) {
    let cars = 0;
    let motorcycles = 0;
    let trucks = 0;
    let buses = 0;
    let other = 0;
    entities.forEach((entity) => {
        if (entity == 'CAR') {
            cars += 1;
        } else if (entity == 'TRUCK') {
            trucks += 1;
        } else if (entity == 'MOTORCYCLE') {
            motorcycles += 1;
        } else if (entity == 'BUS') {
            buses += 1;
        } else if (entity == 'OTHER') {
            other += 1;
        }
    });

    $(`#accident-card-num-cars`)[0].innerHTML =  cars;
    $(`#accident-card-num-motorcycles`)[0].innerHTML = motorcycles;
    $(`#accident-card-num-trucks`)[0].innerHTML = trucks;
    $(`#accident-card-num-buses`)[0].innerHTML = buses;
    $(`#accident-card-num-other`)[0].innerHTML = other;
}

function populateFatalityInfo(fatality) {
    $(`#accident-card-num-light-injuries`)[0].innerHTML = fatality['light_injuries'];
    $(`#accident-card-num-serious-injuries`)[0].innerHTML = fatality['serious_injuries'];
    $(`#accident-card-num-deaths`)[0].innerHTML = fatality['deaths'];
}

function populateClimateInfo(climate) {
    $(`#accident-card-precipitation`)[0].innerHTML = climate['precipitation_mm'];
    $(`#accident-card-wind-speed`)[0].innerHTML = climate['wind_speed_ms'];
    $(`#accident-card-relative-humidity`)[0].innerHTML = climate['relative_humidity_percentage'] * 100;
    $(`#accident-card-visibility`)[0].innerHTML = climate['visibility'] * 100;
    $(`#accident-card-air-temperature`)[0].innerHTML = climate['air_temp_celsius'];
}

function populateLocationInfo(address) {
    $(`#accident-card-street1`)[0].innerHTML = capitalizeSentence(address['street1']);
    $(`#accident-card-street2`)[0].innerHTML = capitalizeSentence(address['street2']);
    $(`#accident-card-region`)[0].innerHTML = capitalizeString(address['region']);
    $(`#accident-card-lat-long`)[0].innerHTML = `${address['location']['latitude']}, ${address['location']['longitude']}`;
}

function populateAccidentContentToCard(accident) {
    populateTitle(accident);
    populateVehicleInfo(accident['involved_entities']);
    populateFatalityInfo(accident['fatality']);
    populateClimateInfo(accident['climate'])
    populateLocationInfo(accident['address']);
}

function displayAccidentDetails(accident) {
    let accidentId = accident['externalId'];

    if (accidentId != displayedAccidentId) {
        populateAccidentContentToCard(accident);
        updateAccidentIdLabel(accidentId);
    }

    if (!displayedAccidentId || accidentId == displayedAccidentId) {
        $('#accident-card')[0].click();
    }

    displayedAccidentId = accidentId;
}
