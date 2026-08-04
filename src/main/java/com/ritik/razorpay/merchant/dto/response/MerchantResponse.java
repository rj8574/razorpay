package com.ritik.razorpay.merchant.dto.response;


import com.ritik.razorpay.common.enums.BusinessType;
import com.ritik.razorpay.common.enums.MerchantStaus;


import java.util.UUID;

public record MerchantResponse (

    UUID id,
    String name,
    String email,
    String businessName,
    BusinessType businessType,
    MerchantStaus merchantStatus
    ){
}
