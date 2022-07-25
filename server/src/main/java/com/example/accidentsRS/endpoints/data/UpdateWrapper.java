package com.example.accidentsRS.endpoints.data;

import java.util.Map;

public class UpdateWrapper {

    private Map<String, Object> filters;
    private Map<String, Object> values;

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
