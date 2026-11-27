package com.ritik.razorpay.payment.dto.response;

import com.ritik.razorpay.common.entity.Money;
import com.ritik.razorpay.common.enums.OrderStatus;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID merchantId,
        String receipt,
        Money money,
        OrderStatus status,
        Integer attempts,
        Map<String,Object> notes,
        LocalDateTime expireAt,
        LocalDateTime createdAt
) {
}
