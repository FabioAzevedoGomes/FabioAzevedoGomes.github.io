class ClimateFilterRow extends FilterRow {

    climateFilterOptions = `
      <option value="${FilterRowNames.Visibility}">Visibility</option>
      <option value="${FilterRowNames.RelativeHumidity}">Relative Humidity</option>
      <option value="${FilterRowNames.Precipitation}">Precipitation</option>
      <option value="${FilterRowNames.WindSpeed}">Wind Speed</option>
      <option value="${FilterRowNames.AirTemperature}">Air Temperature</option>
    `

    buildInitialRowBody() {
        return this.buildEditableRowBody(
            FilterRowNames.Visibility,
            FilterClasses.Climate,
            this.climateFilterOptions,
            "new ClimateFilterRow().updateFilterOptions()",
            "new ClimateFilterRow().addFilterRow()"
        );
    }

    buildEditRowBody() {
        let filterAttribute = this.getSelectedFilterAttribute(FilterClasses.Climate);
        if (filterAttribute) {
            let selectedAttribute = filterAttribute.value;
            return this.buildEditableRowBody(
                selectedAttribute,
                FilterClasses.Climate,
                this.climateFilterOptions,
                "new ClimateFilterRow().updateFilterOptions()",
                "new ClimateFilterRow().addFilterRow()"
            );
        }
    }

    updateFilterOptions() {
        let filterClass = FilterClasses.Climate;
        this.updateOptionsAndValue(FilterClasses.Climate, this.buildEditRowBody());
    }

    addFilterRow() {
        this.removeFilterRow(0);
        var attributeSelector = this.getSelectedFilterAttribute(FilterClasses.Climate);
        var operatorSelector = this.getOperatorSelector(FilterClasses.Climate);
        var selectedFiltersList = this.getSelectedFilterList(FilterClasses.Climate);
        var selectionParent = this.getAddAFilterRow(FilterClasses.Climate);

        if (selectedFiltersList && attributeSelector && operatorSelector && selectionParent) {
            var uniqueFilterId = getUniqueUuid();

            var newFilterHTMLbody = filterAppliedRowTemplate
                .replaceAll(filterIdPlaceholder, uniqueFilterId)
                .replaceAll(appliedFilterPropertyPlaceholder, attributeSelector.selectedOptions[0].value)
                .replaceAll(appliedFilterOpcodePlaceholder, operatorSelector.selectedOptions[0].value)
                .replaceAll(appliedFilterValuePlaceholder, selectionParent.children[2].children[0].value)
                .replaceAll(appliedFilterTextPlaceholder, `${attributeSelector.selectedOptions[0].text} ${operatorSelector.selectedOptions[0].text} "${selectionParent.children[2].children[0].value}"`)
                .replaceAll(removeFilterRowFunctionPlaceholder, "new ClimateFilterRow().removeFilterRow")
                .replaceAll(filterTypePlaceholder, FilterClasses.Climate);

            selectedFiltersList.innerHTML = selectedFiltersList.innerHTML + newFilterHTMLbody;
        }
        refreshMap();
    }

    removeFilterRow(filterId) {
        var filterDiv = this.getAppliedFilterRow(FilterClasses.Climate, filterId);
        if (filterDiv) {
            filterDiv.remove();
        }

        var parent = this.getSelectedFilterList(FilterClasses.Climate);
        if (filterId != noFilterId && parent && parent.childElementCount == 0) {
            parent.innerHTML = defaultNoFilterSelectedRowTemplate.replace(filterTypePlaceholder, FilterClasses.Climate);
        }

        if (filterId != noFilterId) {
            refreshMap();
        }
    }
}
