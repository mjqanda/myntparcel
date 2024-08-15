package com.example.model;

import lombok.Data;

@Data
public class ParcelResponse {
    private double cost;

    public ParcelResponse(double cost) {
        this.cost = cost;
    }
}
