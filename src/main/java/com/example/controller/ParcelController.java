package com.example.controller;

import com.example.model.ParcelRequest;
import com.example.model.ParcelResponse;
import com.example.service.ParcelCostService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {

    private final ParcelCostService parcelCostService;

    public ParcelController(ParcelCostService parcelCostService) {
        this.parcelCostService = parcelCostService;
    }

    @PostMapping("/calculate-cost")
    public ParcelResponse calculateParcelCost(@Valid @RequestBody ParcelRequest request) {
        return parcelCostService.calculateCost(request);
    }
}
