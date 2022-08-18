package com.example.accidentsRS.model.filter;

import java.util.List;

public final class FilterWrapperModel {

    private List<String> fields;
    private OperationEnum operation;
    private Object value;

    public FilterWrapperModel() {
    }

    public FilterWrapperModel(final List<String> fields, final OperationEnum operation, final Object value) {
        this.fields = fields;
        this.operation = operation;
        this.value = value;
    }

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

    public String toString() {
        return String.format("%s %s %s", fields.toString(), operation.label, value);
    }
}
