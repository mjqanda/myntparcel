package com.example.service;

import com.example.exception.InvalidWeightException;
import com.example.model.ParcelRequest;
import com.example.model.ParcelResponse;
import com.example.model.VoucherResponse;
import com.example.util.VolumeCalculator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ParcelCostServiceImpl implements ParcelCostService {

    private static final double HEAVY_PARCEL_COST_MULTIPLIER = 20.0;
    private static final double SMALL_PARCEL_COST_MULTIPLIER = 0.03;
    private static final double MEDIUM_PARCEL_COST_MULTIPLIER = 0.04;
    private static final double LARGE_PARCEL_COST_MULTIPLIER = 0.05;

    @Value("${voucher.api.base.url}")
    private String voucherApiBaseUrl;

    @Value("${voucher.api.key}")
    private String apiKey;

    @Override
    public ParcelResponse calculateCost(ParcelRequest request) {
        double weight = request.getWeight();
        double volume = VolumeCalculator.calculateVolume(request.getHeight(), request.getWidth(), request.getLength());

        if (weight > 50) {
            throw new InvalidWeightException("Weight exceeds the limit of 50kg");
        }

        double cost;
        if (weight > 10) {
            cost = HEAVY_PARCEL_COST_MULTIPLIER * weight;
        } else if (volume < 1500) {
            cost = SMALL_PARCEL_COST_MULTIPLIER * volume;
        } else if (volume < 2500) {
            cost = MEDIUM_PARCEL_COST_MULTIPLIER * volume;
        } else {
            cost = LARGE_PARCEL_COST_MULTIPLIER * volume;
        }

        if (request.getVoucherCode() != null) {
            cost = applyVoucherDiscount(cost, request.getVoucherCode());
        }

        return new ParcelResponse(cost);
    }

    private double applyVoucherDiscount(double cost, String voucherCode) {
        String url = voucherApiBaseUrl + "/voucher/" + voucherCode + "?key=" + apiKey;
        try {
            ResponseEntity<VoucherResponse> response = new RestTemplate()
                    .getForEntity(url, VoucherResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                VoucherResponse voucher = response.getBody();
                if (voucher != null && voucher.getDiscount() != null) {
                    cost -= (cost * voucher.getDiscount() / 100);
                }
            }
        } catch (Exception e) {
            // proceed without applying the discount/ Error 504 handling
            e.printStackTrace();
        }
        return cost;
    }
}
