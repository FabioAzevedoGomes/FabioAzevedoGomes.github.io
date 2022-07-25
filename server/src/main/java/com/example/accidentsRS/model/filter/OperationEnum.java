package com.example.accidentsRS.model.filter;

public enum OperationEnum {
    IS("IS"),
    IS_NOT("IS_NOT"),
    HAS("HAS"),
    DOES_NOT_HAVE("DOES_NOT_HAVE"),
    EQUALS("EQUALS"),
    GREATER_OR_EQUAL("GREATER_OR_EQUAL"),
    LESS_OR_EQUAL("LESS_OR_EQUAL"),
    BEFORE("BEFORE"),
    AFTER("AFTER");

    public final String label;

    private OperationEnum(final String label) {
        this.label = label;
    }
}
