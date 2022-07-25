function showSpecificDataView() {
    document.getElementById("specific-data-overlay").style.display = "block";
}

function hideSpecificDataView() {
    document.getElementById("specific-data-overlay").style.display = "none";
}

function displayDetailsPopup(accidentData) {

    console.log(accidentData);

    document.getElementById("accident-id").textContent = accidentData.id;
    document.getElementById("accident-type").textContent = accidentData.type;
    document.getElementById("latitude-slot").textContent = accidentData.address.location.latitude;
    document.getElementById("longitude-slot").textContent = accidentData.address.location.longitude;
    document.getElementById("region-slot").textContent = accidentData.address.region;
    document.getElementById("street1-slot").textContent = accidentData.address.street1;
    document.getElementById("street2-slot").textContent = accidentData.address.street2;
    //document.getElementById("date-slot").textContent = accidentData.date.date.split("T")[0];
    document.getElementById("weekday-slot").textContent = accidentData.date.weekday;
    //document.getElementById("time-slot").textContent = accidentData.date.hour.split("T")[1].split("+")[0];
    document.getElementById("accident-light-inj").textContent = accidentData.fatality.light_injuries;
    document.getElementById("accident-serious-inj").textContent = accidentData.fatality.serious_injuries;
    document.getElementById("accident-deaths").textContent = accidentData.fatality.deaths;
    document.getElementById("num-vehicles").textContent = accidentData.involved_entities.length;

    let listOfVehicles = "";
    for (let i = 0; i < accidentData.involved_entities.length; i++)  {
        listOfVehicles += "<li>" + accidentData.involved_entities[i] + "</li>\n";
    }
    document.getElementById("vehicle-list").innerHTML = listOfVehicles;
    
    showSpecificDataView();
}
