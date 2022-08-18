package com.example.accidentsRS.endpoints.data;

import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.data.UpdateData;

import java.util.List;

public class UpdateWrapper {
    private List<FilterWrapperData> filters;
    private List<UpdateData> updates;

    public UpdateWrapper() {
    }

    public UpdateWrapper(final List<FilterWrapperData> filters, final List<UpdateData> updates) {
        this.filters = filters;
        this.updates = updates;
    }

    public List<FilterWrapperData> getFilters() {
        return filters;
    }

    public void setFilters(final List<FilterWrapperData> filters) {
        this.filters = filters;
    }

    public List<UpdateData> getUpdates() {
        return updates;
    }

    public void setUpdates(final List<UpdateData> updates) {
        this.updates = updates;
    }
}
