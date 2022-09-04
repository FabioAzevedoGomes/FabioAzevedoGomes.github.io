package com.example.accidentsRS.pathfind.graph;

import com.example.accidentsRS.model.DirectionalStreetModel;

public class Edge {
    private static final Float PENALIZATION_BY_RISK = 100.0f;

    public DirectionalStreetModel data;
    public Float weight;

    public Edge(final DirectionalStreetModel data) {
        this.data = data;
        this.weight = (1.0f + data.getRisk()) * PENALIZATION_BY_RISK * data.getLength();
    }

    public Edge(final DirectionalStreetModel data, final Float weight) {
        this.data = data;
        this.weight = weight;
    }

    public DirectionalStreetModel getData() {
        return data;
    }

    public void setData(DirectionalStreetModel data) {
        this.data = data;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
