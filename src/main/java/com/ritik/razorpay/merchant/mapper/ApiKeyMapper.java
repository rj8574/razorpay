package com.ritik.razorpay.merchant.mapper;

import com.ritik.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.ritik.razorpay.merchant.dto.response.ApiKeyResponse;
import com.ritik.razorpay.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {

    ApiKeyCreateResponse toCreateResponse(ApiKey apiKey);

    List<ApiKeyResponse> toResponseList(List<ApiKey> apiKeyList);
}
