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

function removeFilter(filterClass) {
    hideFilter(defaultFilterTabName.replace(filterTypePlaceholder, filterClass));

    var filterList = $(`#${defaultSelectedFilterListId.replace(filterTypePlaceholder, filterClass)}`)[0];
    if (filterList) {
        filterList.innerHTML = defaultNoFilterSelectedRowTemplate.replace(filterTypePlaceholder, filterClass);;
    }

    globalMap.reloadLayer(LayerNames.Accident);
}

function addFilter(filterClass) {
    showFilter(defaultFilterTabName.replace(filterTypePlaceholder, filterClass));
}

function initFilter(filterClass, classInstance) {
    var addFilter = $(`#${defaultAddAFilterRowId}`.replace(filterTypePlaceholder, filterClass))[0];
    addFilter.innerHTML = classInstance.buildInitialRowBody();
    var selectedFilters = $(`#${defaultSelectedFilterListId}`.replace(filterTypePlaceholder, filterClass))[0];
    selectedFilters.innerHTML = defaultNoFilterSelectedRowTemplate.replace(filterTypePlaceholder, filterClass);
}

class FilterRow {

    fetchOptionsForAtrribute(filteredAttribute, fetchUrl, filterClass) {
        if (fetchUrl) {
            $.ajax(fetchUrl, {
                type: 'GET',
                success: function (result) {
                    var inputOptionsId = filterInputIdTemplate
                        .replaceAll(filterTypePlaceholder, filterClass)
                        .replaceAll(filterAttributePlaceholder, filteredAttribute);
                    var optionsParent = $(`#${inputOptionsId}`)[0];

                    if (result && optionsParent) {
                        optionsParent.innerHTML = "";
                        result.forEach((option) => {
                            var newOption = `'<option value="${option}">${option}</option>`;
                            optionsParent.innerHTML += newOption;
                        });
                    }
                },
                error: function (result) {
                    alert(`Error getting ${filteredAttribute} data from backend`);
                }
            });
        }
    }

    getAttributeOptionsWithSelected(attributeOptions, filteredAttribute) {
        return attributeOptions.replace(`${filteredAttribute}"`, `${filteredAttribute}" selected`);
    }

    getInputTemplate(filterClass, filteredAttribute) {
        return attributeToPropertiesMap.get(filteredAttribute)[1]
            .replaceAll(filterTypePlaceholder, filterClass)
            .replaceAll(filterAttributePlaceholder, filteredAttribute);
    }

    getOperatorOptions(filteredAttribute) {
        return attributeToPropertiesMap.get(filteredAttribute)[0];
    }

    getSelectedFilterAttribute(filterClass) {
        return $(`#${defaultFilterAttributeSelectorId.replace(filterTypePlaceholder, filterClass)}`)[0];
    }

    getOperatorSelector(filterClass) {
        return $(`#${defaultFilterOptionSelectorId.replace(filterTypePlaceholder, filterClass)}`)[0];
    }

    getSelectedFilterList(filterClass) {
        return $(`#${defaultSelectedFilterListId.replace(filterTypePlaceholder, filterClass)}`)[0];
    }

    getAddAFilterRow(filterClass) {
        return $(`#${defaultAddAFilterRowId.replace(filterTypePlaceholder, filterClass)}`)[0];
    }

    getAppliedFilterRow(filterClass, filterId) {
        return $(`#${defaultAppliedFilterRowId.replace(filterTypePlaceholder, filterClass).replace(filterIdPlaceholder, filterId)}`)[0];
    }

    updateOptionsAndValue(filterClass, htmlBody) {
        var attributeSelector = $(`#${defaultFilterAttributeSelectorId.replace(filterTypePlaceholder, filterClass)}`)[0];
        if (attributeSelector) {
            var selection = attributeSelector.value;
            if (selection) {
                var selectedFilterHTML = htmlBody;
                if (selectedFilterHTML) {
                    var addAFilterRow = this.getAddAFilterRow(filterClass);
                    addAFilterRow.innerHTML = selectedFilterHTML;
                }
            }
        }
    }

    buildEditableRowBody(selectedAttribute, filterClass, filterOptions, updateFunctionCall, addFunctionCall) {
        let attributeOptions = this.getAttributeOptionsWithSelected(filterOptions, selectedAttribute);
        let operatorOptions = this.getOperatorOptions(selectedAttribute);
        let inputTemplate = this.getInputTemplate(filterClass, selectedAttribute);

        return filterEditRowTemplate
            .replaceAll(possibleOperatorOptionsPlaceholder, operatorOptions)
            .replaceAll(filterInputTypePlaceholder, inputTemplate)
            .replaceAll(updateAdditionOptionsFunctionPlaceholder, updateFunctionCall)
            .replaceAll(filterTypePlaceholder, filterClass)
            .replaceAll(addFilterRowFunctionPlaceholder, addFunctionCall)
            .replaceAll(possibleFilterOptionsPlaceholder, attributeOptions);
    }
}

function initializeFilters() {
    initFilter(FilterClasses.Accident, new AccidentFilterRow());
    initFilter(FilterClasses.Time, new TimeFilterRow());
    initFilter(FilterClasses.Climate, new ClimateFilterRow());
    initFilter(FilterClasses.Location, new LocationFilterRow());
    new TimeFilterRow().initializeDatePicker();
}
