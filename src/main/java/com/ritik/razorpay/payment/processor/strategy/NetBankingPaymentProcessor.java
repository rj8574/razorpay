package com.ritik.razorpay.payment.processor.strategy;

import com.ritik.razorpay.common.utils.RandomizerUtil;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorResponse;
import com.ritik.razorpay.payment.processor.PaymentProcessor;

public class NetBankingPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {

        final String BANK_CODE_FAIL ="BANK_CODE_FAIL";

        String bankCode =request.methodDetails() !=null ?
                request.methodDetails().get("bank").toString():null;

        if(BANK_CODE_FAIL.equals(bankCode)){
            return new PaymentProcessorResponse.Failure("BANK_REJECETED",
                    "Banked rejected the transition registration");

        }
        String processorRef ="NBK_PROCESSOR_"+ RandomizerUtil.randomBase64(16);
        String redirectRef="http://REDIRECT_BANK.com"+processorRef;


        return new PaymentProcessorResponse.Success(processorRef,redirectRef);
    }
}
