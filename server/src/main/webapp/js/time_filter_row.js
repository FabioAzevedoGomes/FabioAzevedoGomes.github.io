class TimeFilterRow extends FilterRow {

    timeFilterOptions = `
      <option value="${FilterRowNames.Date}">Date</option>
      <option value="${FilterRowNames.Time}">Time</option>
    `

    initializeDatePicker() {
        $(function () {
            $('#time-date-filter').datetimepicker({
                format: 'DD/MM/YYYY'
            });
        });
    }

    initializeTimePicker() {
        $(function () {
            $('#time-time-filter').datetimepicker({
                format: 'LT'
            });
        });
    }

    buildInitialRowBody() {
        return this.buildEditableRowBody(
            FilterRowNames.Date,
            FilterClasses.Time,
            this.timeFilterOptions,
            "new TimeFilterRow().updateFilterOptions()",
            "new TimeFilterRow().addFilterRow()"
        );
    }

    buildEditRowBody() {
        let filterAttribute = this.getSelectedFilterAttribute(FilterClasses.Time);
        if (filterAttribute) {
            let selectedAttribute = filterAttribute.value;
            return this.buildEditableRowBody(
                selectedAttribute,
                FilterClasses.Time,
                this.timeFilterOptions,
                "new TimeFilterRow().updateFilterOptions()",
                "new TimeFilterRow().addFilterRow()"
            );
        }
    }

    updateFilterOptions() {
        let filterClass = FilterClasses.Time;
        this.updateOptionsAndValue(FilterClasses.Time, this.buildEditRowBody());
        var attributeSelector = $(`#${defaultFilterAttributeSelectorId.replace(filterTypePlaceholder, filterClass)}`)[0];
        if (attributeSelector) {
            var selection = attributeSelector.value;
            if (selection == FilterRowNames.Time) {
                this.initializeTimePicker();
            }
            if (selection == FilterRowNames.Date) {
                this.initializeDatePicker();
            }
        }
    }

    addFilterRow() {
        this.removeFilterRow(0);
        var attributeSelector = this.getSelectedFilterAttribute(FilterClasses.Time);
        var operatorSelector = this.getOperatorSelector(FilterClasses.Time);
        var selectedFiltersList = this.getSelectedFilterList(FilterClasses.Time);
        var selectionParent = this.getAddAFilterRow(FilterClasses.Time);

        if (selectedFiltersList && attributeSelector && operatorSelector && selectionParent) {
            var uniqueFilterId = getUniqueUuid();

            var newFilterHTMLbody = filterAppliedRowTemplate
                .replaceAll(filterIdPlaceholder, uniqueFilterId)
                .replaceAll(appliedFilterPropertyPlaceholder, attributeSelector.selectedOptions[0].value)
                .replaceAll(appliedFilterOpcodePlaceholder, operatorSelector.selectedOptions[0].value)
                .replaceAll(appliedFilterValuePlaceholder, selectionParent.children[2].children[0].children[0].value)
                .replaceAll(appliedFilterTextPlaceholder, `${attributeSelector.selectedOptions[0].text} ${operatorSelector.selectedOptions[0].text} "${selectionParent.children[2].children[0].children[0].value}"`)
                .replaceAll(removeFilterRowFunctionPlaceholder, "new TimeFilterRow().removeFilterRow")
                .replaceAll(filterTypePlaceholder, FilterClasses.Time);

            selectedFiltersList.innerHTML = selectedFiltersList.innerHTML + newFilterHTMLbody;
        }
        refreshMap();
    }

    removeFilterRow(filterId) {
        var filterDiv = this.getAppliedFilterRow(FilterClasses.Time, filterId);
        if (filterDiv) {
            filterDiv.remove();
        }

        var parent = this.getSelectedFilterList(FilterClasses.Time);
        if (filterId != noFilterId && parent && parent.childElementCount == 0) {
            parent.innerHTML = defaultNoFilterSelectedRowTemplate.replace(filterTypePlaceholder, FilterClasses.Time);
        }

        if (filterId != noFilterId) {
            refreshMap();
        }
    }
}