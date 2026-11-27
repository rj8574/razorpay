package com.ritik.razorpay.payment.controller;

import com.ritik.razorpay.payment.dto.request.PaymentInitRequest;
import com.ritik.razorpay.payment.dto.response.PaymentResponse;
import com.ritik.razorpay.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/v1/payments")
@RestController
@RequiredArgsConstructor
public class paymentController {

    private final PaymentService paymentService;
    UUID merchantId = UUID.fromString("6e98edb6-f933-464f-baeb-e8f17efd016d");


    @PostMapping
    public ResponseEntity<PaymentResponse> initiate(@Valid @RequestBody PaymentInitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.initiate(merchantId, request));
    }

    @PostMapping("{paymentId}/capture")
    public ResponseEntity<PaymentResponse> capture(@PathVariable UUID paymentId) {
        return ResponseEntity.ok(paymentService.capture(merchantId,paymentId));
    }
}
