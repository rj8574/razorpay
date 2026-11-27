package com.ritik.razorpay.payment.repository;

import com.ritik.razorpay.payment.entity.OrderRecord;
import com.ritik.razorpay.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrder_Id(OrderRecord order);

    Optional<Payment> findByIdAndMerchantID(UUID paymentId, UUID merchantId);
}
