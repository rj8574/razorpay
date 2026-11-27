package com.ritik.razorpay.payment.service;

import com.ritik.razorpay.payment.dto.request.CreateRequestOrder;
import com.ritik.razorpay.payment.dto.response.OrderResponse;
import com.ritik.razorpay.payment.dto.response.PaymentResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponse create(UUID merchantId, CreateRequestOrder request);

    OrderResponse getById(UUID merchantId, UUID orderId);

    OrderResponse cancel(UUID merchantId, UUID orderId);

    List<PaymentResponse> listPayments(UUID merchantId, UUID orderId);
}
