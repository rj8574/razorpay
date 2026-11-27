package com.ritik.razorpay.payment.dto.request;

import com.ritik.razorpay.common.entity.Money;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Map;

public record CreateRequestOrder(
        @NotNull(message = "Amount is required")
        Money amount,

        @Size(max =100)
        String receipt,
        Map<String, Object> notes,
        LocalDateTime expiresAt
 ) {
}
