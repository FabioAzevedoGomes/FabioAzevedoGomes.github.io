package com.example.accidentsRS.pathfind.graph;

import com.example.accidentsRS.model.IntersectionModel;

public class Node {
    public IntersectionModel data;
    public float weight;

    public IntersectionModel getData() {
        return data;
    }

    public Node(final IntersectionModel data) {
        this.data = data;
        this.weight = 0.0f;
    }

    public void setData(IntersectionModel data) {
        this.data = data;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(final float weight) {
        this.weight = weight;
    }
}
