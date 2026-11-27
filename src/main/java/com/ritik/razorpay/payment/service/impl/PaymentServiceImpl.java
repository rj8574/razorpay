package com.ritik.razorpay.payment.service.impl;

import com.ritik.razorpay.common.enums.OrderStatus;
import com.ritik.razorpay.common.enums.PaymentEvent;
import com.ritik.razorpay.common.enums.PaymentStatus;
import com.ritik.razorpay.common.exception.BusinessRuleViolationExcepetion;
import com.ritik.razorpay.common.exception.ResourceNotFoundException;
import com.ritik.razorpay.payment.dto.request.PaymentInitRequest;
import com.ritik.razorpay.payment.dto.response.PaymentResponse;
import com.ritik.razorpay.payment.entity.OrderRecord;
import com.ritik.razorpay.payment.entity.Payment;
import com.ritik.razorpay.payment.gateway.PaymentGatewayRouter;
import com.ritik.razorpay.payment.gateway.dto.PaymentRequest;
import com.ritik.razorpay.payment.gateway.dto.PaymentResult;
import com.ritik.razorpay.payment.mapper.PaymentMapper;
import com.ritik.razorpay.payment.repository.OrderRepository;
import com.ritik.razorpay.payment.repository.PaymentRepository;
import com.ritik.razorpay.payment.service.PaymentService;
import com.ritik.razorpay.payment.stateMachine.PaymentStateMachine;
import com.ritik.razorpay.payment.stateMachine.PaymentTransitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayRouter  paymentGatewayRouter;
    private final PaymentMapper paymentMapper;
    private final PaymentTransitionService paymentTransitionService;

    @Override
    @Transactional
    public PaymentResponse initiate(UUID merchantId, PaymentInitRequest request) {
         OrderRecord order=orderRepository.findByIdAndMerchantId(request.OrderId(), merchantId).orElseThrow(
                 ()-> new ResourceNotFoundException("Order" ,request.OrderId()));

         if(order.getOrderStatus() != OrderStatus.CREATED && order.getOrderStatus() !=OrderStatus.ATTEMPTED)
         {
             throw new BusinessRuleViolationExcepetion("ORDER_NOT_AVAILABLE",
                     "order cannot accpet payment in status"+ order.getOrderStatus());}
         order.setOrderStatus(OrderStatus.ATTEMPTED);
         order.setAttempts(order.getAttempts()+1);

        Payment payment= Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .amount(order.getAmount())
                .status(PaymentStatus.CREATED)
                .method(request.method())
                .methodDetails(request.methodDetails())
                .build();
        payment= paymentRepository.save(payment);

        PaymentRequest paymentRequest =new PaymentRequest(
                payment.getId(),
                request.OrderId(),
                merchantId,
                order.getAmount(),
                request.method(),
                request.methodDetails());
        PaymentResult result=paymentGatewayRouter.initiate(paymentRequest);

        switch (result) {
            case PaymentResult.Pending pending -> payment.setProcessorReference(pending.registrationRef());
            case PaymentResult.Failure failure -> {
                paymentTransitionService.apply(payment, PaymentEvent.AUTHORIZE_FAILURE);
                payment.setErrorCode(failure.errorCode());
                payment.setErrorDescription(failure.errorDescription());
            }
            case PaymentResult.Success success -> {

            }

        }
        payment = paymentRepository.save(payment);
        orderRepository.save(order);
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse capture(UUID merchantId, UUID paymentId) {
        Payment payment=paymentRepository.findByIdAndMerchantID(paymentId,merchantId).orElseThrow(
                ()-> new ResourceNotFoundException("Payment" ,paymentId));


        paymentTransitionService.apply(payment , PaymentEvent.CAPTURE_REQUEST);
        PaymentResult paymentResult = paymentGatewayRouter.capture(payment.getMethod(),paymentId);
        if(paymentResult instanceof PaymentResult.Success success) {

            paymentTransitionService.apply(payment, PaymentEvent.CAPTURE_SUCCESS);
            payment.setCapturedAt(LocalDateTime.now());
            log.info("Payment Captured paymentID: {}",paymentId);
        } else if (paymentResult instanceof PaymentResult.Failure failure) {

            paymentTransitionService.apply(payment, PaymentEvent.CAPTURE_FAILURE);
            payment.setErrorCode(failure.errorCode());
            payment.setErrorDescription(failure.errorDescription());
            log.warn("Payment Captured failed, paymentID: {}",paymentId);

            payment=paymentRepository.save(payment);

            return paymentMapper.toResponse(payment);

        }



        return null;
    }


}
