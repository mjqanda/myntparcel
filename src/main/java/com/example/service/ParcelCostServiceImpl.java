package com.example.service;

import com.example.model.ParcelRequest;
import com.example.model.ParcelResponse;
import com.example.model.VoucherResponse;
import com.example.util.VolumeCalculator;

import org.springframework.stereotype.Service;

@Service
public class ParcelCostServiceImpl implements ParcelCostService {

    private static final double HEAVY_PARCEL_COST_MULTIPLIER = 20.0;
    private static final double SMALL_PARCEL_COST_MULTIPLIER = 0.03;
    private static final double MEDIUM_PARCEL_COST_MULTIPLIER = 0.04;
    private static final double LARGE_PARCEL_COST_MULTIPLIER = 0.05;

    private final VoucherService voucherService;

    public ParcelCostServiceImpl(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public ParcelResponse calculateCost(ParcelRequest request) {
        double weight = request.getWeight();
        double volume = VolumeCalculator.calculateVolume(request.getHeight(), request.getWidth(), request.getLength());

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
        VoucherResponse voucher = voucherService.fetchVoucher(voucherCode);

        if (voucher != null && voucher.getDiscount() != null) {
            if (voucherService.isVoucherValid(voucher)) {
                cost -= (cost * voucher.getDiscount() / 100);
            } else {
                System.out.println("Voucher is expired.");
            }
        }
        return cost;
    }
}
