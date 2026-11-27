package com.ritik.razorpay.payment.gateway.adapter;

import com.ritik.razorpay.common.enums.PaymentMethod;
import com.ritik.razorpay.payment.gateway.PaymentAdapter;
import com.ritik.razorpay.payment.gateway.dto.PaymentRequest;
import com.ritik.razorpay.payment.gateway.dto.PaymentResult;
import com.ritik.razorpay.payment.processor.PaymentProcessorRouter;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.ritik.razorpay.payment.processor.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class NetBankingAdpater implements PaymentAdapter {

    private final PaymentProcessorRouter paymentProcessorRouter;
    @Override
    public PaymentResult initiate(PaymentRequest request) {

        log.info("initiate with NetBankingAdapter,paymentId:{}",request.paymentId());
        try {
            PaymentProcessorRequest paymentProcessorRequest = PaymentProcessorRequest.nonCard(
                    request.paymentId(),
                    PaymentMethod.NETBANKING,
                    request.amount(),
                    request.methodDetails()
            );
            PaymentProcessorResponse paymentProcessorResponse = paymentProcessorRouter.charge(paymentProcessorRequest);


            return switch (paymentProcessorResponse) {
                case PaymentProcessorResponse.Failure failure ->
                        new PaymentResult.Failure(failure.errorCode(), failure.errorDescription());

                case PaymentProcessorResponse.Pending pending ->
                        new PaymentResult.Pending(pending.processorReference());

                case PaymentProcessorResponse.Success success -> new PaymentResult.Success(success.bankReference());
            };
        }
        catch (Exception e) {
            log.warn("Net banking Failed,paymentId:{}",request.paymentId());
            return new PaymentResult.Failure("Net_banking_Failed",e.getMessage());
        }
    }

    @Override
    public PaymentResult capture(UUID paymentId) {
        return new PaymentResult.Success("NBK_REF");

    }
}
