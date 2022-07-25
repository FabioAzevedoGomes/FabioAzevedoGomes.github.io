<div id="specific-data-area" class="specific-data-view">
    <a href="javascript:void(0)" class="closebtn" onclick="hideSpecificDataView()">&times;</a>
    <div class="specific-data-content">
        <h3>Accident <b><span id="accident-id">X</span></b></h3>
        <div class="type-area">
        <h4>Type: <b><span id="accident-type">?</span></b></h4>
        </div>
        <div class="row">
            <div class="address-area col-lg-6">
                <h5>Address</h5>
                <ul>
                    <li>Latitude: <b><span id="latitude-slot">?</span></b></li>
                    <li>Longitude: <b><span id="longitude-slot">?</span></b></li>
                    <li>Region: <b><span id="region-slot">?</span></b></li>
                    <li>First Street: <b><span id="street1-slot">?</span></b></li>
                    <li>Second Street: <b><span id="street2-slot">?</span></b></li>
                </ul>
            </div>
            <div class="date-area col-lg-6">
                <h5>Date</h5>
                <ul>
                    <li>Day: <b><span id="date-slot">?</span></b></li>
                    <li>Weekday: <b><span id="weekday-slot">?</span></b></li>
                    <li>Time: <b><span id="time-slot">?</span></b></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="fatality-area col-lg-6">
                <h5>Light Injuries: <b><span id="accident-light-inj">?</span></b></h5>
                <h5>Serious Injuries: <b><span id="accident-serious-inj">?</span></b></h5>
                <h5>Deaths: <b><span id="accident-deaths">?</span></b></h5>
            </div>
            <div class="vehicles-area col-lg-6">
                <h5>Involved vehicles: <b><span id="num-vehicles"></span></b></h5>
                <ul id="vehicle-list"></ul>
            </div>
        </div>
    </div>
</div>
<script src="/js/specific-data.js"></script>