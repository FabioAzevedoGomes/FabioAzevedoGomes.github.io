package com.example.accidentsRS.model.filter;

import java.util.List;

public final class FilterWrapperModel {

    private List<String> fields;
    private OperationEnum operation;
    private Object value;

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public OperationEnum getOperation() {
        return operation;
    }

    public void setOperation(OperationEnum operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
