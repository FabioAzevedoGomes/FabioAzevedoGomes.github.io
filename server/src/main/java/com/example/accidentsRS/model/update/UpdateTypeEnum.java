package com.example.accidentsRS.model.update;

public enum UpdateTypeEnum {
    SET("set"),
    PUSH("push");

    public final String label;

    UpdateTypeEnum(final String label) {
        this.label = label;
    }
}
