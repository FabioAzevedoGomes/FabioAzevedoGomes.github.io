package com.example.accidentsRS.model.wrapper;

import com.example.accidentsRS.model.IntersectionModel;

public class IntersectionModelWrapper {

    public static final String DATA = "data";
    private IntersectionModel data;

    public IntersectionModel getData() {
        return data;
    }

    public void setData(IntersectionModel data) {
        this.data = data;
    }
}
