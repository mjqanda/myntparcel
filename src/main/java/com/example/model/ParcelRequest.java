package com.example.model;

import lombok.Data;

@Data
public class ParcelRequest {
    private double weight;
    private double height;
    private double width;
    private double length;
    private String voucherCode;
}
