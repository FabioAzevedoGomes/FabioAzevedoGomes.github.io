package com.example.accidentsRS.pathfind.graph;

import com.example.accidentsRS.model.DirectionalStreetModel;

public class Edge {
    public DirectionalStreetModel data;
    public Float weight;

    public Edge(final DirectionalStreetModel data) {
        this.data = data;
        this.weight = data.getLength();
    }

    public Edge(final DirectionalStreetModel data, final Float weight) {
        this.data = data;
        this.weight = (1.0f + weight) * data.getLength();
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
