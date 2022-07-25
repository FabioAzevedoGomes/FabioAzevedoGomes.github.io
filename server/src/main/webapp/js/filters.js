const FilterClasses = {
	Accident: "accidents-filter-tab",
	Climate: "climate-filter-tab",
	Location: "location-filter-tab",
	Time: "time-filter-tab"
}

function hideFilter(elementName) {
	var accidentFilterTab = document.getElementById(elementName);
	if (accidentFilterTab) {
		accidentFilterTab.style.display = 'none';
	}
}

function showFilter(elementName) {
	var accidentFilterTab = document.getElementById(elementName);
	if (accidentFilterTab) {
		accidentFilterTab.style.display = 'flex';
	}	
}

function getUniqueUuid() {
	return Date.now().toString(36) + Math.random().toString(36).substr(2);
}


// ACCIDENT FILTERS

const accidentFilterSelectorMap = new Map()

accidentFilterSelectorMap.set('accidents-id-filter',`

    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-accident-filter-attribute-selector" onchange="updateAccidentFilterAdditionOptions()">
          <option selected value="accidents-id-filter">Identifier</option>
          <option value="accidents-death-filter">Deaths</option>
          <option value="accidents-light-inj-filter">Light Injuries</option>
          <option value="accidents-serious-inj-filter">Serious Injuries</option>
          <option value="accidents-type-filter">Type</option>
          <option value="accidents-vehicle-filter">Vehicles</option>
        </select>
    </div>
    <div class="col"> <!-- Can change depending on filter-->
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-filter-operator-selector">
		  <option value="IS">is</option>
		  <option value="IS NOT">is not</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="text" id="accidents-id-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addAccidentFilterRow()">+</button>
    </div>
`);
accidentFilterSelectorMap.set('accidents-type-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-accident-filter-attribute-selector" onchange="updateAccidentFilterAdditionOptions()">
          <option value="accidents-id-filter">Identifier</option>
          <option value="accidents-death-filter">Deaths</option>
          <option value="accidents-light-inj-filter">Light Injuries</option>
          <option value="accidents-serious-inj-filter">Serious Injuries</option>
          <option selected value="accidents-type-filter">Type</option>
          <option value="accidents-vehicle-filter">Vehicles</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-filter-operator-selector">
		  <option value="IS">is</option>
		  <option value="IS NOT">is not</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select form-input" id="accidents-type-filter">
            <option value="dummy">dummy</option> <!-- Retrieve and populate from backend -->
        </select>
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addAccidentFilterRow()">+</button>
    </div>
`);
accidentFilterSelectorMap.set('accidents-vehicle-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-accident-filter-attribute-selector" onchange="updateAccidentFilterAdditionOptions()">
          <option value="accidents-id-filter">Identifier</option>
          <option value="accidents-death-filter">Deaths</option>
          <option value="accidents-light-inj-filter">Light Injuries</option>
          <option value="accidents-serious-inj-filter">Serious Injuries</option>
          <option value="accidents-type-filter">Type</option>
          <option selected value="accidents-vehicle-filter">Vehicles</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-filter-operator-selector">
		  <option value="HAS">has</option>
		  <option value="DOES_NOT_HAVE">does not have</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select form-input" id="accidents-vehicle-filter">
            <option value="dummy">dummy</option> <!-- Retrieve and populate from backend -->
        </select>
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addAccidentFilterRow()">+</button>
    </div>
`);
accidentFilterSelectorMap.set('accidents-light-inj-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-accident-filter-attribute-selector" onchange="updateAccidentFilterAdditionOptions()">
          <option value="accidents-id-filter">Identifier</option>
          <option value="accidents-death-filter">Deaths</option>
          <option selected value="accidents-light-inj-filter">Light Injuries</option>
          <option value="accidents-serious-inj-filter">Serious Injuries</option>
          <option value="accidents-type-filter">Type</option>
          <option value="accidents-vehicle-filter">Vehicles</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-filter-operator-selector">
		  <option value="EQUALS">=</option>
		  <option value="GREATER_OR_EQUAL">>=</option>
		  <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" id="accidents-light-inj-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addAccidentFilterRow()">+</button>
    </div>
`);
accidentFilterSelectorMap.set('accidents-serious-inj-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-accident-filter-attribute-selector" onchange="updateAccidentFilterAdditionOptions()">
          <option value="accidents-id-filter">Identifier</option>
          <option value="accidents-death-filter">Deaths</option>
          <option value="accidents-light-inj-filter">Light Injuries</option>
          <option selected value="accidents-serious-inj-filter">Serious Injuries</option>
          <option value="accidents-type-filter">Type</option>
          <option value="accidents-vehicle-filter">Vehicles</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-filter-operator-selector">
		  <option value="EQUALS">=</option>
		  <option value="GREATER_OR_EQUAL">>=</option>
		  <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" id="accidents-serious-inj-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addAccidentFilterRow()">+</button>
    </div>
`);
accidentFilterSelectorMap.set('accidents-death-filter', `
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-accident-filter-attribute-selector" onchange="updateAccidentFilterAdditionOptions()">
          <option value="accidents-id-filter">Identifier</option>
          <option selected value="accidents-death-filter">Deaths</option>
          <option value="accidents-light-inj-filter">Light Injuries</option>
          <option value="accidents-serious-inj-filter">Serious Injuries</option>
          <option value="accidents-type-filter">Type</option>
          <option value="accidents-vehicle-filter">Vehicles</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-filter-operator-selector">
		  <option value="EQUALS">=</option>
		  <option value="GREATER_OR_EQUAL">>=</option>
		  <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" id="accidents-death-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addAccidentFilterRow()">+</button>
    </div>
`);
const defaultAccidentNoFilterSelected = `
    <div class= "form-row" id="accident-filter-id-0">
        <div class="col">
            No filters active.
        </div>                         
    </div>
`;
const defaultAccidentSelectedFilter = `
	<div class= "form-row" id="accident-filter-id-$(filter_id)">
		<div class="col">
			PLACEHOLDER
		</div>
        <div id="field-name" style="display: none;">
            FIELDNAME
        </div>
        <div id="operation" style="display: none;">
            OPCODE
        </div>
        <div id="value" style="display: none;">
            FIELDVALUE
        </div>
		<div class="col-lg-2">
			<button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeAccidentFilterRow(\'$(filter_id)\');">X</button>
		</div>								
	</div>
`;

function updateAccidentTypeOptions() {
    $.ajax('/accidents/get/types', {
        type: 'GET',
        success: function(result) {
            var optionsParent = document.getElementById('accidents-type-filter');
            if (result && optionsParent) {
                optionsParent.innerHTML = "";
                result.forEach((type) => {
                    var newOption = '<option value="' + type + '">' + type + '</option>';
                    optionsParent.innerHTML += newOption;
                });
            }
        },
        error: function(result) {
            alert('Error getting accident type data from backend');
        }
    });
}

function updateAccidentVehicleOptions() {
    $.ajax('/accidents/get/vehicles', {
        type: 'GET',
        success: function(result) {
            var optionsParent = document.getElementById('accidents-vehicle-filter');
            if (result && optionsParent) {
                optionsParent.innerHTML = "";
                result.forEach((type) => {
                    var newOption = '<option value="' + type + '">' + type + '</option> ';
                    optionsParent.innerHTML += newOption;
                });
            }
        },
        error: function(result) {
            alert('Error getting accident vehicle data from backend');
        }
    });
}

function updateAccidentFilterAdditionOptions() {
	var attributeSelector = document.getElementById('default-accident-filter-attribute-selector');

	if (attributeSelector) {
		var selection = attributeSelector.value;
		if (selection) {
			var selectedFilterHTML = accidentFilterSelectorMap.get(selection);
			if (selectedFilterHTML) {
				var addAFilterRow = document.getElementById('accidents-add-a-filter-row');
				addAFilterRow.innerHTML = selectedFilterHTML;
				if (selection == 'accidents-type-filter') {
                    updateAccidentTypeOptions();
                } else if (selection == 'accidents-vehicle-filter') {
                    updateAccidentVehicleOptions();
                }
                
			}
		}
	}
}

function addAccidentFilterRow() {
    removeAccidentFilterRow(0);
	var attributeSelector = document.getElementById('default-accident-filter-attribute-selector');
	var operatorSelector = document.getElementById('default-filter-operator-selector');
	var selectedFiltersList = document.getElementById('accidents-selected-filters');
    var selectionParent = document.getElementById('accidents-add-a-filter-row');

	if (selectedFiltersList && attributeSelector && operatorSelector && selectionParent) {
		var uniqueFilterId = getUniqueUuid();
		var newFilterHTML = defaultAccidentSelectedFilter.replaceAll('$(filter_id)', uniqueFilterId);
        newFilterHTML = newFilterHTML.replaceAll('FIELDNAME', attributeSelector.selectedOptions[0].value);
        newFilterHTML = newFilterHTML.replaceAll('OPCODE', operatorSelector.selectedOptions[0].value);
        newFilterHTML = newFilterHTML.replaceAll('FIELDVALUE', selectionParent.children[2].children[0].value);
        newFilterHTML = newFilterHTML.replaceAll('PLACEHOLDER', attributeSelector.selectedOptions[0].text
            + ' '
            + operatorSelector.selectedOptions[0].text
            + ' \"'
            + selectionParent.children[2].children[0].value
            + "\"");

		selectedFiltersList.innerHTML = selectedFiltersList.innerHTML + newFilterHTML;
	}
    refreshMap();
}

function removeAccidentFilterRow(filter_id) {
	var filterDiv = document.getElementById('accident-filter-id-' + filter_id);
	if (filterDiv) {
		filterDiv.remove();
	}

    var parent = document.getElementById('accidents-selected-filters');
    if (filter_id != "0" && parent && parent.childElementCount == 0) {
        parent.innerHTML = defaultAccidentNoFilterSelected;
    }

    console.log("removed filter row" + filter_id);

    if (filter_id != "0") {
        refreshMap();
    }
}

function removeAccidentFilter() {

	// Hide tab
	hideFilter(FilterClasses.Accident);

	var accidentFilterList = document.getElementById('accidents-selected-filters');
	if (accidentFilterList) {
		accidentFilterList.innerHTML = defaultAccidentNoFilterSelected;
	}

    refreshMap();
}

function addAccidentFilter() {
	// Show tab
	showFilter(FilterClasses.Accident);
}


// TIME FILTERS

const timeFilterSelectorMap = new Map();

timeFilterSelectorMap.set('time-date-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-time-filter-attribute-selector" onchange="updateTimeFilterAdditionOptions()">
          <option selected value="time-date-filter">Date</option>
          <option value="time-time-filter">Time</option>
        </select>
    </div>
    <div class="col"> <!-- Can change depending on filter-->
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-time-filter-operator-selector">
          <option value="IS">is</option>
          <option value="BEFORE">is before</option>
          <option value="AFTER">is after</option>
        </select>
    </div>
    <div class="col date">
        <div class="input-group date" id="time-date-filter" data-target-input="nearest">
            <input type="text" class="form-control datetimepicker-input" data-target="#time-date-filter"/>
            <div class="input-group-append" data-target="#time-date-filter" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-clock-o"></i></div>
            </div>
        </div>
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addTimeFilterRow()">+</button>
    </div>
`);
timeFilterSelectorMap.set('time-time-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-time-filter-attribute-selector" onchange="updateTimeFilterAdditionOptions()">
          <option value="time-date-filter">Date</option>
          <option selected value="time-time-filter">Time</option>
        </select>
    </div>
    <div class="col"> <!-- Can change depending on filter-->
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-time-filter-operator-selector">
          <option value="IS">is</option>
          <option value="BEFORE">is before</option>
          <option value="AFTER">is after</option>
        </select>
    </div>
    <div class="col date">
        <div class="input-group date" id="time-time-filter" data-target-input="nearest">
            <input type="text" class="form-control datetimepicker-input" data-target="#time-time-filter"/>
            <div class="input-group-append" data-target="#time-time-filter" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-clock-o"></i></div>
            </div>
        </div>
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addTimeFilterRow()">+</button>
    </div>
`);
const defaultTimeNoFilterSelected = `
    <div class= "form-row" id="time-filter-id-0">
        <div class="col">
            No filters active.
        </div>                         
    </div>
`;
const defaultTimeSelectedFilter = `
    <div class= "form-row" id="time-filter-id-$(filter_id)">
        <div class="col">
            PLACEHOLDER
        </div>
        <div id="field-name" style="display: none;">
            FIELDNAME
        </div>
        <div id="operation" style="display: none;">
            OPCODE
        </div>
        <div id="value" style="display: none;">
            FIELDVALUE
        </div>
        <div class="col-lg-2">
            <button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeTimeFilterRow(\'$(filter_id)\');">X</button>
        </div>                              
    </div>
`;

function updateTimeFilterAdditionOptions() {
    var attributeSelector = document.getElementById('default-time-filter-attribute-selector');

    if (attributeSelector) {
        var selection = attributeSelector.value;
        if (selection) {
            var selectedFilterHTML = timeFilterSelectorMap.get(selection);
            if (selectedFilterHTML) {
                var addAFilterRow = document.getElementById('time-add-a-filter-row');
                addAFilterRow.innerHTML = selectedFilterHTML;
                if (selection == 'time-date-filter') {
                    $(function () {
                        $('#time-date-filter').datetimepicker({
                            format: 'DD/MM/YYYY'
                        });
                    });
                } else if (selection == 'time-time-filter') {
                    $(function () {
                        $('#time-time-filter').datetimepicker({
                            format: 'LT'
                        });
                    });
                }
            }
        }
    }
}
    
function addTimeFilterRow() {
    removeTimeFilterRow(0);
    var attributeSelector = document.getElementById('default-time-filter-attribute-selector');
    var operatorSelector = document.getElementById('default-time-filter-operator-selector');
    var selectedFiltersList = document.getElementById('time-selected-filters');
    var selectionParent = document.getElementById('time-add-a-filter-row');

    if (selectedFiltersList && attributeSelector && operatorSelector && selectionParent) {
        var uniqueFilterId = getUniqueUuid();
        var newFilterHTML = defaultTimeSelectedFilter.replaceAll('$(filter_id)', uniqueFilterId);
        newFilterHTML = newFilterHTML.replaceAll('FIELDNAME', attributeSelector.selectedOptions[0].value);
        newFilterHTML = newFilterHTML.replaceAll('OPCODE', operatorSelector.selectedOptions[0].value);
        newFilterHTML = newFilterHTML.replaceAll('FIELDVALUE', selectionParent.children[2].children[0].children[0].value);
        var newFilterHTML = newFilterHTML.replaceAll('PLACEHOLDER', attributeSelector.selectedOptions[0].text
            + ' '
            + operatorSelector.selectedOptions[0].text
            + ' \"'
            + selectionParent.children[2].children[0].children[0].value
            + "\"");

        selectedFiltersList.innerHTML = selectedFiltersList.innerHTML + newFilterHTML;
    }
    refreshMap();
}

function removeTimeFilterRow(filter_id) {
    var filterDiv = document.getElementById('time-filter-id-' + filter_id);
    if (filterDiv) {
        filterDiv.remove();
    }

    var parent = document.getElementById('time-selected-filters');
    if (filter_id != "0" && parent && parent.childElementCount == 0) {
        parent.innerHTML = defaultTimeNoFilterSelected;
    }

    if (filter_id != "0") {
        refreshMap();
    }
}

function removeTimeFilter() {

	// Hide tab
	hideFilter(FilterClasses.Time);

    var timeFilterList = document.getElementById('time-selected-filters');
    if (timeFilterList) {
        timeFilterList.innerHTML = defaultTimeNoFilterSelected;
    }

    refreshMap();
}

function addTimeFilter() {
	// Show tab
	showFilter(FilterClasses.Time);
}

// CLIMATE FILTERS

const climateFilterSelectorMap = new Map()

climateFilterSelectorMap.set('climate-visibility-filter', `
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-climate-filter-attribute-selector" onchange="updateClimateFilterAdditionOptions()">
          <option selected value="climate-visibility-filter">Visibility</option>
          <option value="climate-relative-humidity-percent-filter">Relative Humidity</option>
          <option value="climate-precipitation-filter">Precipitation</option>
          <option value="climate-wind-speed-filter">Wind Speed</option>
          <option value="climate-air-temp-filter">Air Temperature</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-climate-filter-operator-selector">
          <option value="EQUALS">=</option>
          <option value="GREATER_OR_EQUAL">>=</option>
          <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" step="0.01" id="climate-visibility-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addClimateFilterRow()">+</button>
    </div>
`);
climateFilterSelectorMap.set('climate-relative-humidity-percent-filter', `
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-climate-filter-attribute-selector" onchange="updateClimateFilterAdditionOptions()">
          <option value="climate-visibility-filter">Visibility</option>
          <option selected value="climate-relative-humidity-percent-filter">Relative Humidity</option>
          <option value="climate-precipitation-filter">Precipitation</option>
          <option value="climate-wind-speed-filter">Wind Speed</option>
          <option value="climate-air-temp-filter">Air Temperature</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-climate-filter-operator-selector">
          <option value="EQUALS">=</option>
          <option value="GREATER_OR_EQUAL">>=</option>
          <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" step="0.01" id="climate-relative-humidity-percent-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addClimateFilterRow()">+</button>
    </div>
`);
climateFilterSelectorMap.set('climate-precipitation-filter', `
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-climate-filter-attribute-selector" onchange="updateClimateFilterAdditionOptions()">
          <option value="climate-visibility-filter">Visibility</option>
          <option value="climate-relative-humidity-percent-filter">Relative Humidity</option>
          <option selected value="climate-precipitation-filter">Precipitation</option>
          <option value="climate-wind-speed-filter">Wind Speed</option>
          <option value="climate-air-temp-filter">Air Temperature</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-climate-filter-operator-selector">
          <option value="EQUALS">=</option>
          <option value="GREATER_OR_EQUAL">>=</option>
          <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" step="0.01" id="climate-percipitation-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addClimateFilterRow()">+</button>
    </div>
`);
climateFilterSelectorMap.set('climate-wind-speed-filter', `
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-climate-filter-attribute-selector" onchange="updateClimateFilterAdditionOptions()">
          <option value="climate-visibility-filter">Visibility</option>
          <option value="climate-relative-humidity-percent-filter">Relative Humidity</option>
          <option value="climate-precipitation-filter">Precipitation</option>
          <option selected value="climate-wind-speed-filter">Wind Speed</option>
          <option value="climate-air-temp-filter">Air Temperature</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-climate-filter-operator-selector">
          <option value="EQUALS">=</option>
          <option value="GREATER_OR_EQUAL">>=</option>
          <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" step="0.01" id="climate-wind-speed-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addClimateFilterRow()">+</button>
    </div>
`);
climateFilterSelectorMap.set('climate-air-temp-filter', `
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-climate-filter-attribute-selector" onchange="updateClimateFilterAdditionOptions()">
          <option value="climate-visibility-filter">Visibility</option>
          <option value="climate-relative-humidity-percent-filter">Relative Humidity</option>
          <option value="climate-precipitation-filter">Precipitation</option>
          <option value="climate-wind-speed-filter">Wind Speed</option>
          <option selected value="climate-air-temp-filter">Air Temperature</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-climate-filter-operator-selector">
          <option value="EQUALS">=</option>
          <option value="GREATER_OR_EQUAL">>=</option>
          <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" step="0.01" id="climate-air-temp-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addClimateFilterRow()">+</button>
    </div>
`);
const defaultClimateNoFilterSelected = `
    <div class= "form-row" id="climate-filter-id-0">
        <div class="col">
            No filters active.
        </div>                         
    </div>
`;
const defaultClimateSelectedFilter = `
    <div class= "form-row" id="climate-filter-id-$(filter_id)">
        <div class="col">
            PLACEHOLDER
        </div>
        <div id="field-name" style="display: none;">
            FIELDNAME
        </div>
        <div id="operation" style="display: none;">
            OPCODE
        </div>
        <div id="value" style="display: none;">
            FIELDVALUE
        </div>
        <div class="col-lg-2">
            <button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeClimateFilterRow(\'$(filter_id)\');">X</button>
        </div>                              
    </div>
`;

function updateClimateFilterAdditionOptions() {
    var attributeSelector = document.getElementById('default-climate-filter-attribute-selector');

    if (attributeSelector) {
        var selection = attributeSelector.value;
        if (selection) {
            var selectedFilterHTML = climateFilterSelectorMap.get(selection);
            if (selectedFilterHTML) {
                var addAFilterRow = document.getElementById('climate-add-a-filter-row');
                addAFilterRow.innerHTML = selectedFilterHTML;
            }
        }
    }
}

function addClimateFilterRow() {
    removeClimateFilterRow(0);
    var attributeSelector = document.getElementById('default-climate-filter-attribute-selector');
    var operatorSelector = document.getElementById('default-climate-filter-operator-selector');
    var selectedFiltersList = document.getElementById('climate-selected-filters');
    var selectionParent = document.getElementById('climate-add-a-filter-row');

    if (selectedFiltersList && attributeSelector && operatorSelector && selectionParent) {
        var uniqueFilterId = getUniqueUuid();
        var newFilterHTML = defaultClimateSelectedFilter.replaceAll('$(filter_id)', uniqueFilterId);
        newFilterHTML = newFilterHTML.replaceAll('FIELDNAME', attributeSelector.selectedOptions[0].value);
        newFilterHTML = newFilterHTML.replaceAll('OPCODE', operatorSelector.selectedOptions[0].value);
        newFilterHTML = newFilterHTML.replaceAll('FIELDVALUE', selectionParent.children[2].children[0].value);
        newFilterHTML = newFilterHTML.replaceAll('PLACEHOLDER', attributeSelector.selectedOptions[0].text
            + ' '
            + operatorSelector.selectedOptions[0].text
            + ' \"'
            + selectionParent.children[2].children[0].value
            + "\"");

        selectedFiltersList.innerHTML = selectedFiltersList.innerHTML + newFilterHTML;
    }
    refreshMap();
}

function removeClimateFilterRow(filter_id) {
    var filterDiv = document.getElementById('climate-filter-id-' + filter_id);
    if (filterDiv) {
        filterDiv.remove();
    }

    var parent = document.getElementById('climate-selected-filters');
    if (filter_id != "0" && parent && parent.childElementCount == 0) {
        parent.innerHTML = defaultClimateNoFilterSelected;
    }
    
    if (filter_id != "0") {
        refreshMap();
    }
}

function removeClimateFilter() {

	// Hide tab
	hideFilter(FilterClasses.Climate);

    var climateFilterList = document.getElementById('climate-selected-filters');
    if (climateFilterList) {
        climateFilterList.innerHTML = defaultClimateNoFilterSelected;
    }

    refreshMap();
}

function addClimateFilter() {
	// Show tab
	showFilter(FilterClasses.Climate);
}

// LOCATION FILTERS

const locationFilterSelectorMap = new Map();

locationFilterSelectorMap.set('location-street-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-location-filter-attribute-selector" onchange="updateLocationFilterAdditionOptions()">
          <option selected value="location-street-filter">Street</option>
          <option value="location-latitude-filter">Latitude</option>
          <option value="location-longitude-filter">Longitude</option>
          <option value="location-region-filter">Region</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-location-filter-operator-selector" disabled>
          <option value="IS">is</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="text" id="location-street-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addLocationFilterRow()">+</button>
    </div>
`);
locationFilterSelectorMap.set('location-latitude-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-location-filter-attribute-selector" onchange="updateLocationFilterAdditionOptions()">
          <option value="location-street-filter">Street</option>
          <option selected value="location-latitude-filter">Latitude</option>
          <option value="location-longitude-filter">Longitude</option>
          <option value="location-region-filter">Region</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-location-filter-operator-selector">
          <option value="EQUALS">=</option>
          <option value="GREATER_OR_EQUAL">>=</option>
          <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" step="0.0000001" id="location-latitude-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addLocationFilterRow()">+</button>
    </div>
`);
locationFilterSelectorMap.set('location-longitude-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-location-filter-attribute-selector" onchange="updateLocationFilterAdditionOptions()">
          <option value="location-street-filter">Street</option>
          <option value="location-latitude-filter">Latitude</option>
          <option selected value="location-longitude-filter">Longitude</option>
          <option value="location-region-filter">Region</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-location-filter-operator-selector">
          <option value="EQUALS">=</option>
          <option value="GREATER_OR_EQUAL">>=</option>
          <option value="LESS_OR_EQUAL"><=</option>
        </select>
    </div>
    <div class="col">
        <input class="form-control form-input" type="number" step="0.0000001" id="location-longitude-filter">
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addLocationFilterRow()">+</button>
    </div>
`);
locationFilterSelectorMap.set('location-region-filter',`
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-location-filter-attribute-selector" onchange="updateLocationFilterAdditionOptions()">
          <option value="location-street-filter">Street</option>
          <option value="location-latitude-filter">Latitude</option>
          <option value="location-longitude-filter">Longitude</option>
          <option selected value="location-region-filter">Region</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-location-filter-operator-selector">
          <option value="IS">is</option>
          <option value="IS NOT">is not</option>
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select form-input" id="location-region-filter">
            <option value="dummy">dummy</option> <!-- Retrieve and populate from backend -->
        </select>
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addLocationFilterRow()">+</button>
    </div>
`);
const defaultLocationNoFilterSelected = `
    <div class= "form-row" id="location-filter-id-0">
        <div class="col">
            No filters active.
        </div>                         
    </div>
`;
const defaultLocationSelectedFilter = `
    <div class= "form-row" id="location-filter-id-$(filter_id)">
        <div class="col">
            PLACEHOLDER
        </div>
        <div id="field-name" style="display: none;">
            FIELDNAME
        </div>
        <div id="operation" style="display: none;">
            OPCODE
        </div>
        <div id="value" style="display: none;">
            FIELDVALUE
        </div>
        <div class="col-lg-2">
            <button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeLocationFilterRow(\'$(filter_id)\');">X</button>
        </div>                              
    </div>
`;

function updateLocationRegionOptions() {
    $.ajax('/accidents/get/regions', {
        type: 'GET',
        success: function(result) {
            var optionsParent = document.getElementById('location-region-filter');
            if (result && optionsParent) {
                optionsParent.innerHTML = "";
                result.forEach((type) => {
                    var newOption = '<option value="' + type + '">' + type + '</option>';
                    optionsParent.innerHTML += newOption;
                });
            }
        },
        error: function(result) {
            alert('Error getting location region data from backend');
        }
    });
}

function updateLocationFilterAdditionOptions() {
    var attributeSelector = document.getElementById('default-location-filter-attribute-selector');

    if (attributeSelector) {
        var selection = attributeSelector.value;
        if (selection) {
            var selectedFilterHTML = locationFilterSelectorMap.get(selection);
            if (selectedFilterHTML) {
                var addAFilterRow = document.getElementById('location-add-a-filter-row');
                addAFilterRow.innerHTML = selectedFilterHTML;
                if (selection == 'location-region-filter') {
                    updateLocationRegionOptions();
                }
            }
        }
    }
}

function addLocationFilterRow() {
    removeLocationFilterRow(0);
    var attributeSelector = document.getElementById('default-location-filter-attribute-selector');
    var operatorSelector = document.getElementById('default-location-filter-operator-selector');
    var selectedFiltersList = document.getElementById('location-selected-filters');
    var selectionParent = document.getElementById('location-add-a-filter-row');

    if (selectedFiltersList && attributeSelector && operatorSelector && selectionParent) {
        var uniqueFilterId = getUniqueUuid();
        var newFilterHTML = defaultLocationSelectedFilter.replaceAll('$(filter_id)', uniqueFilterId);
        newFilterHTML = newFilterHTML.replaceAll('FIELDNAME', attributeSelector.selectedOptions[0].value);
        newFilterHTML = newFilterHTML.replaceAll('OPCODE', operatorSelector.selectedOptions[0].value);
        newFilterHTML = newFilterHTML.replaceAll('FIELDVALUE', selectionParent.children[2].children[0].value);
        newFilterHTML = newFilterHTML.replaceAll('PLACEHOLDER', attributeSelector.selectedOptions[0].text
            + ' '
            + operatorSelector.selectedOptions[0].text
            + ' \"'
            + selectionParent.children[2].children[0].value
            + "\"");

        selectedFiltersList.innerHTML = selectedFiltersList.innerHTML + newFilterHTML;
    }
    refreshMap();
}

function removeLocationFilterRow(filter_id) {
    var filterDiv = document.getElementById('location-filter-id-' + filter_id);
    if (filterDiv) {
        filterDiv.remove();
    }

    var parent = document.getElementById('location-selected-filters');
    if (filter_id != "0" && parent && parent.childElementCount == 0) {
        parent.innerHTML = defaultLocationNoFilterSelected;
    }

    if (filter_id != "0") {
        refreshMap();
    }
}

function removeLocationFilter() {

	// Hide tab
	hideFilter(FilterClasses.Location);

	// Clear actual filters
    var locationFilterList = document.getElementById('location-selected-filters');
    if (locationFilterList) {
        locationFilterList.innerHTML = defaultLocationNoFilterSelected;
    }

    refreshMap();
}

function addLocationFilter() {
	// Show tab
	showFilter(FilterClasses.Location);
}


document.getElementById('accident-filters-list').addEventListener("click", function(event){
    event.preventDefault()
});
document.getElementById('climate-filters-list').addEventListener("click", function(event){
    event.preventDefault()
});

document.getElementById('location-filters-list').addEventListener("click", function(event){
    event.preventDefault()
});

document.getElementById('time-filters-list').addEventListener("click", function(event){
    event.preventDefault()
});

$(function () {
    $('#time-date-filter').datetimepicker({
        format: 'DD/MM/YYYY'
    });
});

$(function () {
    $('#time-time-filter').datetimepicker({
        format: 'LT'
    });
});