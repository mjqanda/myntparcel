package com.example.model;

import lombok.Data;

@Data
public class VoucherResponse {
    private String code;
    private Integer discount;
    private String expiry;
}
