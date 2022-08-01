class LocationFilterRow extends FilterRow {

    locationFilterOptions = `
      <option value="${FilterRowNames.Street}">Street</option>
      <option value="${FilterRowNames.Latitude}">Latitude</option>
      <option value="${FilterRowNames.Longitude}">Longitude</option>
      <option value="${FilterRowNames.Region}">Region</option>
    `

    regionsUrl = '/accidents/get/regions';

    buildInitialRowBody() {
        return this.buildEditableRowBody(
            FilterRowNames.Street,
            FilterClasses.Location,
            this.locationFilterOptions,
            "new LocationFilterRow().updateFilterOptions()",
            "new LocationFilterRow().addFilterRow()"
        );
    }

    buildEditRowBody() {
        let filterAttribute = this.getSelectedFilterAttribute(FilterClasses.Location);
        if (filterAttribute) {
            let selectedAttribute = filterAttribute.value;
            return this.buildEditableRowBody(
                selectedAttribute,
                FilterClasses.Location,
                this.locationFilterOptions,
                "new LocationFilterRow().updateFilterOptions()",
                "new LocationFilterRow().addFilterRow()"
            );
        }
    }

    updateFilterOptions() {
        let filterClass = FilterClasses.Location;
        this.updateOptionsAndValue(FilterClasses.Location, this.buildEditRowBody());
        var attributeSelector = $(`#${defaultFilterAttributeSelectorId.replace(filterTypePlaceholder, filterClass)}`)[0];
        if (attributeSelector) {
            var selection = attributeSelector.value;
            if (selection == FilterRowNames.Region) {
                this.fetchOptionsForAtrribute(selection, this.regionsUrl, FilterClasses.Location);
            }
        }
    }

    addFilterRow() {
        this.removeFilterRow(0);
        var attributeSelector = this.getSelectedFilterAttribute(FilterClasses.Location);
        var operatorSelector = this.getOperatorSelector(FilterClasses.Location);
        var selectedFiltersList = this.getSelectedFilterList(FilterClasses.Location);
        var selectionParent = this.getAddAFilterRow(FilterClasses.Location);

        if (selectedFiltersList && attributeSelector && operatorSelector && selectionParent) {
            var uniqueFilterId = getUniqueUuid();

            var newFilterHTMLbody = filterAppliedRowTemplate
                .replaceAll(filterIdPlaceholder, uniqueFilterId)
                .replaceAll(appliedFilterPropertyPlaceholder, attributeSelector.selectedOptions[0].value)
                .replaceAll(appliedFilterOpcodePlaceholder, operatorSelector.selectedOptions[0].value)
                .replaceAll(appliedFilterValuePlaceholder, selectionParent.children[2].children[0].value)
                .replaceAll(appliedFilterTextPlaceholder, `${attributeSelector.selectedOptions[0].text} ${operatorSelector.selectedOptions[0].text} "${selectionParent.children[2].children[0].value}"`)
                .replaceAll(removeFilterRowFunctionPlaceholder, "new LocationFilterRow().removeFilterRow")
                .replaceAll(filterTypePlaceholder, FilterClasses.Location);

            selectedFiltersList.innerHTML = selectedFiltersList.innerHTML + newFilterHTMLbody;
        }
        globalMap.reloadLayer(LayerNames.Accident);
    }

    removeFilterRow(filterId) {
        var filterDiv = this.getAppliedFilterRow(FilterClasses.Location, filterId);
        if (filterDiv) {
            filterDiv.remove();
        }

        var parent = this.getSelectedFilterList(FilterClasses.Location);
        if (filterId != noFilterId && parent && parent.childElementCount == 0) {
            parent.innerHTML = defaultNoFilterSelectedRowTemplate.replace(filterTypePlaceholder, FilterClasses.Location);
        }

        if (filterId != noFilterId) {
            globalMap.reloadLayer(LayerNames.Accident);
        }
    }
}
