package com.ritik.razorpay.payment.repository;

import com.ritik.razorpay.payment.entity.PaymentTransitionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTransitionRepository extends JpaRepository<PaymentTransitionLog, UUID> {
}
