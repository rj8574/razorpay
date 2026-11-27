package com.ritik.razorpay.payment.controller;

import com.ritik.razorpay.payment.dto.request.CreateRequestOrder;
import com.ritik.razorpay.payment.dto.response.OrderResponse;
import com.ritik.razorpay.payment.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    UUID merchantId=UUID.fromString("6e98edb6-f933-464f-baeb-e8f17efd016d");
    @PostMapping
    public ResponseEntity<OrderResponse> create (@RequestBody @Valid CreateRequestOrder request)
    {
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                orderService.create(merchantId, request)
        );
    }
}
