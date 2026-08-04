package com.ritik.razorpay.merchant.service;

import com.ritik.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.ritik.razorpay.merchant.dto.response.MerchantResponse;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

public interface AuthService {
     MerchantResponse signup( MerchantSignupRequest request);
}
