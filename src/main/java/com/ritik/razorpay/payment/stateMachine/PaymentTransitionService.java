package com.ritik.razorpay.payment.stateMachine;

import com.ritik.razorpay.common.enums.PaymentActor;
import com.ritik.razorpay.common.enums.PaymentEvent;
import com.ritik.razorpay.common.enums.PaymentStatus;
import com.ritik.razorpay.payment.entity.Payment;
import com.ritik.razorpay.payment.entity.PaymentTransitionLog;
import com.ritik.razorpay.payment.repository.PaymentTransitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentTransitionService {

    private final PaymentTransitionRepository paymentTransitionRepository;
    private final PaymentStateMachine paymentStateMachine;

    public PaymentStatus apply(Payment  payment, PaymentEvent  event) {
        PaymentStatus next= paymentStateMachine.transition(payment.getStatus(),event);
        payment.setStatus(next);

        PaymentTransitionLog log = PaymentTransitionLog.builder()
                .payment(payment)
                .fromStatus(payment.getStatus())
                .event(event)
                .toStatus(next)
                .actor(PaymentActor.SYSTEM)
                .occurredAt(LocalDateTime.now())
                .build();
        paymentTransitionRepository.save(log);
        return next;
    }
}
