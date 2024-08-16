package com.example.service;

import com.example.model.VoucherResponse;

public interface VoucherService {

    VoucherResponse fetchVoucher(String voucherCode);

    boolean isVoucherValid(VoucherResponse voucher);

}