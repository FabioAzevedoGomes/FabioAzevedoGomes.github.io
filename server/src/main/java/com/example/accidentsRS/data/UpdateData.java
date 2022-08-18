package com.example.accidentsRS.data;

import com.example.accidentsRS.model.update.UpdateTypeEnum;

public class UpdateData {

    private String field;
    private UpdateTypeEnum type;
    private Object value;

    public UpdateData() {
    }

    public UpdateData(String field, UpdateTypeEnum type, Object value) {
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
