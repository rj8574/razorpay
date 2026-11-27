package com.ritik.razorpay.payment.mapper;

import com.ritik.razorpay.payment.dto.response.OrderResponse;
import com.ritik.razorpay.payment.entity.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderResponse toResponse(OrderRecord orderRecord);
}
