package com.ritik.razorpay.payment.processor;

import com.ritik.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {
    PaymentProcessorResponse charge(PaymentProcessorRequest request);
}
