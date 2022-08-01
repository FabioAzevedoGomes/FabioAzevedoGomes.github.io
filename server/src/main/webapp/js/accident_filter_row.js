class AccidentFilterRow extends FilterRow {

    accidentFilterOptions = `
        <option value="${FilterRowNames.ExternalId}">Identifier</option>
        <option value="${FilterRowNames.Type}">Type</option>
        <option value="${FilterRowNames.Vehicle}">Vehicles</option>
        <option value="${FilterRowNames.LightInjuries}">Light Injuries</option>
        <option value="${FilterRowNames.SeriousInjuries}">Serious Injuries</option>
        <option value="${FilterRowNames.Deaths}">Deaths</option>
    `

    attributeUrlMap = new Map([
        [FilterRowNames.Vehicle, "/accidents/get/vehicles"],
        [FilterRowNames.Type, "/accidents/get/types"]
    ])

    buildInitialRowBody() {
        return this.buildEditableRowBody(
            FilterRowNames.ExternalId,
            FilterClasses.Accident,
            this.accidentFilterOptions,
            "new AccidentFilterRow().updateFilterOptions()",
            "new AccidentFilterRow().addFilterRow()"
        );
    }

    buildEditRowBody() {
        let filterAttribute = this.getSelectedFilterAttribute(FilterClasses.Accident);
        if (filterAttribute) {
            let selectedAttribute = filterAttribute.value;
            return this.buildEditableRowBody(
                selectedAttribute,
                FilterClasses.Accident,
                this.accidentFilterOptions,
                "new AccidentFilterRow().updateFilterOptions()",
                "new AccidentFilterRow().addFilterRow()"
            );
        }
    }

    updateFilterOptions() {
        let filterClass = FilterClasses.Accident;
        this.updateOptionsAndValue(FilterClasses.Accident, this.buildEditRowBody());
        var attributeSelector = $(`#${defaultFilterAttributeSelectorId.replace(filterTypePlaceholder, filterClass)}`)[0];
        if (attributeSelector) {
            var selection = attributeSelector.value;
            var attribValuesUrl = this.attributeUrlMap.get(selection);
            if (attribValuesUrl) {
                this.fetchOptionsForAtrribute(selection, attribValuesUrl, FilterClasses.Accident);
            }
        }

    }

    addFilterRow() {
        this.removeFilterRow(0);
        var attributeSelector = this.getSelectedFilterAttribute(FilterClasses.Accident);
        var operatorSelector = this.getOperatorSelector(FilterClasses.Accident);
        var selectedFiltersList = this.getSelectedFilterList(FilterClasses.Accident);
        var selectionParent = this.getAddAFilterRow(FilterClasses.Accident);

        if (selectedFiltersList && attributeSelector && operatorSelector && selectionParent) {
            var uniqueFilterId = getUniqueUuid();

            var newFilterHTMLbody = filterAppliedRowTemplate
                .replaceAll(filterIdPlaceholder, uniqueFilterId)
                .replaceAll(appliedFilterPropertyPlaceholder, attributeSelector.selectedOptions[0].value)
                .replaceAll(appliedFilterOpcodePlaceholder, operatorSelector.selectedOptions[0].value)
                .replaceAll(appliedFilterValuePlaceholder, selectionParent.children[2].children[0].value)
                .replaceAll(appliedFilterTextPlaceholder, `${attributeSelector.selectedOptions[0].text} ${operatorSelector.selectedOptions[0].text} "${selectionParent.children[2].children[0].value}"`)
                .replaceAll(removeFilterRowFunctionPlaceholder, "new AccidentFilterRow().removeFilterRow")
                .replaceAll(filterTypePlaceholder, FilterClasses.Accident);

            selectedFiltersList.innerHTML = selectedFiltersList.innerHTML + newFilterHTMLbody;
        }
        globalMap.reloadLayer(LayerNames.Accident);
    }

    removeFilterRow(filterId) {
        var filterDiv = this.getAppliedFilterRow(FilterClasses.Accident, filterId);
        if (filterDiv) {
            filterDiv.remove();
        }

        var parent = this.getSelectedFilterList(FilterClasses.Accident);
        if (filterId != noFilterId && parent && parent.childElementCount == 0) {
            parent.innerHTML = defaultNoFilterSelectedRowTemplate.replace(filterTypePlaceholder, FilterClasses.Accident);
        }

        if (filterId != noFilterId) {
            globalMap.reloadLayer(LayerNames.Accident);
        }
    }
}
