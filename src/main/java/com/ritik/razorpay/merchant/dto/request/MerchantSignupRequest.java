package com.ritik.razorpay.merchant.dto.request;

import com.ritik.razorpay.common.enums.BusinessType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record MerchantSignupRequest (

    @NotNull(message = "name is required")
    @Size(max = 50, message = "Name should not be more than 50 characters long")
    String name,

    @NotNull
    @Size(message = "email is required")
    String email,

    @NotNull(message = "password is required")
    @Size(min=8 , message = "password should be at least 8 characters long")
    String password,

    @Size (max = 50, message = "name should be not more than 50 characters long")
    String businessName,

    BusinessType businessType
    ){

}
