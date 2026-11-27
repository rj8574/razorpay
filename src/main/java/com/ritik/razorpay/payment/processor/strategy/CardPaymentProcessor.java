package com.ritik.razorpay.payment.processor.strategy;

import com.ritik.razorpay.common.utils.RandomizerUtil;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorResponse;
import com.ritik.razorpay.payment.processor.PaymentProcessor;

public class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        final String VPA_CODE_FAIL ="fail@okaxis";

        String bankCode =request.methodDetails() !=null ?
                request.methodDetails().get("vpa").toString():null;

        if(VPA_CODE_FAIL.equals(bankCode)){
            return new PaymentProcessorResponse.Failure("CARD_REJECTED",
                    "Banked rejected the transition registration");

        }
        String processorRef ="UPI_PROCESSOR_"+ RandomizerUtil.randomBase64(16);
        String bankRef="http://REDIRECT_BANK.com"+processorRef;


        return new PaymentProcessorResponse.Success(processorRef,bankRef);

    }
}
