package com.example.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.example.model.VoucherResponse;

@Service
public class VoucherServiceImpl implements VoucherService{
    
    @Value("${voucher.api.base.url}")
    private String voucherApiBaseUrl;

    @Value("${voucher.api.key}")
    private String apiKey;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public VoucherResponse fetchVoucher(String voucherCode) {
        String url = voucherApiBaseUrl + "/voucher/" + voucherCode + "?key=" + apiKey;
        try {
            ResponseEntity<VoucherResponse> response = new RestTemplate().getForEntity(url, VoucherResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (ResourceAccessException e) {
            throw new ResourceAccessException("Error encountered while accessing the voucher service.");
        }
        return null;
    }

    public boolean isVoucherValid(VoucherResponse voucher) {
        if (voucher == null || voucher.getExpiry() == null) {
            return false;
        }

        try {
            LocalDate expiryDate = LocalDate.parse(voucher.getExpiry(), DATE_FORMATTER);
            return expiryDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format for voucher expiry.");
            return false;
        }
    }
}
