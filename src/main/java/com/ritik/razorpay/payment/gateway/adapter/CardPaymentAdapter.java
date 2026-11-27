package com.ritik.razorpay.payment.gateway.adapter;

import com.ritik.razorpay.payment.gateway.PaymentAdapter;
import com.ritik.razorpay.payment.gateway.dto.PaymentRequest;
import com.ritik.razorpay.payment.gateway.dto.PaymentResult;

import java.util.UUID;

public class CardPaymentAdapter implements PaymentAdapter {
    @Override
    public PaymentResult initiate(PaymentRequest request) {
    return null;
    }

    @Override
    public PaymentResult capture(UUID paymentId) {
        return new PaymentResult.Success("CARD_REF");
    }
}
