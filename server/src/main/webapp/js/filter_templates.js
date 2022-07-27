const filterTypePlaceholder = `$(filter-type)`;
const filterAttributePlaceholder = `$(filter-attribute)`;
const filterIdPlaceholder = `$(filter_id)`;
const updateAdditionOptionsFunctionPlaceholder = `$(filter-update-options-function)`;
const possibleFilterOptionsPlaceholder = `$(filter-options)`;
const possibleOperatorOptionsPlaceholder = `$(filter-option-operators)`
const filterInputTypePlaceholder =`$(filter-input-type)`
const addFilterRowFunctionPlaceholder = `$(filter-add-row-function)`;
const removeFilterRowFunctionPlaceholder = `$(filter-remove-row-function)`;
const defaultFilterAttributeSelectorId = `default-${filterTypePlaceholder}-filter-attribute-selector`;
const defaultFilterOptionSelectorId = `default-${filterTypePlaceholder}-filter-operator-selector`;
const defaultSelectedFilterListId = `${filterTypePlaceholder}-selected-filters`;
const defaultAddAFilterRowId = `${filterTypePlaceholder}-add-a-filter-row`;
const defaultFilterTabName = `${filterTypePlaceholder}-filter-tab`;
const filterEditRowTemplate = `
    <div class="col">
        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="${defaultFilterAttributeSelectorId}" onchange="${updateAdditionOptionsFunctionPlaceholder}">
          ${possibleFilterOptionsPlaceholder}
        </select>
    </div>
    <div class="col">
        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="${defaultFilterOptionSelectorId}">
            ${possibleOperatorOptionsPlaceholder}
        </select>
    </div>
    <div class="col">
        ${filterInputTypePlaceholder}
    </div>
    <div class="col-lg-2">
        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="${addFilterRowFunctionPlaceholder}">
            <i class="fa-solid fa-plus"></i>
        </button>
    </div>
`;
const defaultAppliedFilterRowId = `${filterTypePlaceholder}-filter-id-${filterIdPlaceholder}`;
const appliedFilterTextPlaceholder = `$(applied-filter-text)`;
const appliedFilterPropertyPlaceholder = `$(applied-filter-prop)`;
const appliedFilterOpcodePlaceholder = `$(applied-filter-opcode)`;
const appliedFilterValuePlaceholder = `$(applied-filter-value)`;
const filterAppliedRowTemplate = `
    <div class= "form-row" id="${defaultAppliedFilterRowId}">
        <div class="col">
            ${appliedFilterTextPlaceholder}
        </div>
        <div id="field-name" style="display: none;">
            ${appliedFilterPropertyPlaceholder}
        </div>
        <div id="operation" style="display: none;">
            ${appliedFilterOpcodePlaceholder}
        </div>
        <div id="value" style="display: none;">
            ${appliedFilterValuePlaceholder}
        </div>
        <div class="col-lg-2">
            <button type="button" class="btn btn-outline-danger remove-filter-button" onclick="${removeFilterRowFunctionPlaceholder}(\'${filterIdPlaceholder}\');">
                <i class="fa-solid fa-x"></i>
            </button>
        </div>                              
    </div>
`;
const noFilterId = "0";
const defaultNoFilterSelectedRowTemplate = `
    <div class= "form-row" id="${filterTypePlaceholder}-filter-id-${noFilterId}">
        <div class="col">
            No filters active.
        </div>                         
    </div>
`;
const FilterClasses = {
    Accident: "accident",
    Climate: "climate",
    Location: "location",
    Time: "time"
};
const FilterOptions = {
    Identity: `<option value="IS">is</option><option value="IS NOT">is not</option>`,
    IdentityStrict: `<option value="IS">is</option>`,
    Containing: `<option value="HAS">has</option><option value="DOES_NOT_HAVE">does not have</option>`,
    Numbering: `<option value="EQUALS">=</option><option value="GREATER_OR_EQUAL">>=</option><option value="LESS_OR_EQUAL"><=</option>`,
    Timing: `<option value="IS">is</option><option value="BEFORE">is before</option><option value="AFTER">is after</option>`
};
const filterInputIdTemplate = `${filterTypePlaceholder}-${filterAttributePlaceholder}-filter`;
const FilterValueTypes = {
    Text: `<input class="form-control form-input" type="text" id="${filterInputIdTemplate}">`,
    Integer: `<input class="form-control form-input" type="number" id="${filterInputIdTemplate}">`,
    Float: `<input class="form-control form-input" type="number" step="0.0000001" id="${filterInputIdTemplate}">`,
    Options: `<select class="form-control form-select form-input" id="${filterInputIdTemplate}"></select>`,
    DateTime: `
    <div class="input-group date" id="${filterInputIdTemplate}" data-target-input="nearest">
        <input type="text" class="form-control datetimepicker-input" data-target="#${filterInputIdTemplate}"/>
        <div class="input-group-append" data-target="#${filterInputIdTemplate}" data-toggle="datetimepicker">
            <div class="input-group-text">
                <i class="fa fa-clock" aria-hidden="true"></i>
            </div>
        </div>
    </div>`
}
const FilterRowNames = {
    ExternalId: 'external-id',
    Type: 'type',
    Vehicle: 'vehicle',
    LightInjuries: 'light-injuries',
    SeriousInjuries: 'serious-injuries',
    Deaths: 'deaths',
    Visibility : 'visibility',
    RelativeHumidity: 'relative-humidity',
    Precipitation: 'precipitation',
    WindSpeed: 'wind-speed',
    AirTemperature: 'air-temperature',
    Street: 'street',
    Latitude: 'latitude',
    Longitude: 'longitude',
    Region: 'region',
    Time: 'time',
    Date: 'date'
}

const attributeToPropertiesMap = new Map([
    [FilterRowNames.ExternalId,
        [FilterOptions.Identity, FilterValueTypes.Text]
    ],
    [FilterRowNames.Type,
        [FilterOptions.Identity, FilterValueTypes.Options]
    ],
    [FilterRowNames.Vehicle,
        [FilterOptions.Containing, FilterValueTypes.Options]
    ],
    [FilterRowNames.LightInjuries,
        [FilterOptions.Numbering, FilterValueTypes.Integer]
    ],
    [FilterRowNames.SeriousInjuries,
        [FilterOptions.Numbering, FilterValueTypes.Integer]
    ],
    [FilterRowNames.Deaths,
        [FilterOptions.Numbering, FilterValueTypes.Integer]
    ],
    [FilterRowNames.Visibility,
        [FilterOptions.Numbering, FilterValueTypes.Float]
    ],
    [FilterRowNames.RelativeHumidity,
        [FilterOptions.Numbering, FilterValueTypes.Float]
    ],
    [FilterRowNames.Precipitation,
        [FilterOptions.Numbering, FilterValueTypes.Float]
    ],
    [FilterRowNames.WindSpeed,
        [FilterOptions.Numbering, FilterValueTypes.Float]
    ],
    [FilterRowNames.AirTemperature,
        [FilterOptions.Numbering, FilterValueTypes.Float]
    ],
    [FilterRowNames.Street,
        [FilterOptions.IdentityStrict, FilterValueTypes.Text]
    ],
    [FilterRowNames.Latitude,
        [FilterOptions.Numbering, FilterValueTypes.Float]
    ],
    [FilterRowNames.Longitude,
        [FilterOptions.Numbering, FilterValueTypes.Float]
    ],
    [FilterRowNames.Region,
        [FilterOptions.Identity, FilterValueTypes.Options]
    ],
    [FilterRowNames.Time,
        [FilterOptions.Timing, FilterValueTypes.DateTime]
    ],
    [FilterRowNames.Date,
        [FilterOptions.Timing, FilterValueTypes.DateTime]
    ]
]);