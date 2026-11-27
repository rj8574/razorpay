package com.ritik.razorpay.merchant.dto.request;

import com.ritik.razorpay.common.enums.Environment;


public record CreateApiKeyRequest(
     Environment environment
) {}
