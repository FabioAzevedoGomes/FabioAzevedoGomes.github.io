package com.example.accidentsRS.model.update;

public class UpdateModel {

    public static final String FIELD = "field";
    private String field;
    public static final String TYPE = "type";
    private UpdateTypeEnum type;
    public static final String VALUE = "value";
    private Object value;

    public UpdateModel() {
    }

    public UpdateModel(String field, UpdateTypeEnum type, Object value) {
        this.field = field;
        this.type = type;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public UpdateTypeEnum getType() {
        return type;
    }

    public void setType(UpdateTypeEnum type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
