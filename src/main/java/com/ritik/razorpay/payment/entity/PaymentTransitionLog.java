package com.ritik.razorpay.payment.entity;

import com.ritik.razorpay.common.entity.BaseEntity;
import com.ritik.razorpay.common.enums.PaymentActor;
import com.ritik.razorpay.common.enums.PaymentEvent;
import com.ritik.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_transition_log",indexes = {
        @Index(name = "idx_payment_transition_log_payment_id", columnList = "payment_id")
})
public class PaymentTransitionLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status" , length = 20)
    private PaymentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "event",nullable = false, length = 30)
    private PaymentEvent event;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status", length = 20)
    private PaymentStatus toStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor", length = 100)
    private PaymentActor actor;

    @Column(name = "Occurred_At", nullable = false)
    private LocalDateTime occurredAt;
}
