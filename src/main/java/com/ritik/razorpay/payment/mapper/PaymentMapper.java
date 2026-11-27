package com.ritik.razorpay.payment.mapper;

import com.ritik.razorpay.payment.dto.response.PaymentResponse;
import com.ritik.razorpay.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper
{
    @Mapping(target = "orderId", source = "order.id")
    PaymentResponse toResponse(Payment payment);
}
