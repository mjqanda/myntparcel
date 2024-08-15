package com.example.service;

import com.example.model.ParcelRequest;
import com.example.model.ParcelResponse;

public interface ParcelCostService {
    ParcelResponse calculateCost(ParcelRequest request);
}
