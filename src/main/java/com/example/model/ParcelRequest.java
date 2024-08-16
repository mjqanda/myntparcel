package com.example.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ParcelRequest {
    @NotNull(message = "Weight is required")
    @Min(value = 0, message = "Weight must be positive")
    @Max(value = 50, message = "Weight exceeds the limit of 50kg")
    private Double weight;

    @NotNull(message = "Height is required")
    @Min(value = 0, message = "Height must be positive")
    private Double height;

    @NotNull(message = "Width is required")
    @Min(value = 0, message = "Width must be positive")
    private Double width;

    @NotNull(message = "Length is required")
    @Min(value = 0, message = "Length must be positive")
    private Double length;

    private String voucherCode;
}
