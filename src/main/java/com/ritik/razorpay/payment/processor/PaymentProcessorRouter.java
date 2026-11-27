package com.ritik.razorpay.payment.processor;

import com.ritik.razorpay.common.enums.PaymentMethod;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorResponse;

import java.util.Map;

public class PaymentProcessorRouter {

    private Map<PaymentMethod,PaymentProcessor> paymentProcessors;
    public PaymentProcessorResponse charge(PaymentProcessorRequest request){

        PaymentProcessor processor=paymentProcessors.get(request.method());
        if(processor==null){
            throw new IllegalArgumentException("No PaymentProcessor registered for method "+request.method());
        }
        return processor.charge(request);

    }
}
