package com.ritik.razorpay.merchant.service.impl;

import com.ritik.razorpay.common.exception.ResourceNotFoundException;
import com.ritik.razorpay.common.utils.RandomizerUtil;
import com.ritik.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.ritik.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.ritik.razorpay.merchant.dto.response.ApiKeyResponse;
import com.ritik.razorpay.merchant.entity.ApiKey;
import com.ritik.razorpay.merchant.entity.Merchant;
import com.ritik.razorpay.merchant.mapper.ApiKeyMapper;
import com.ritik.razorpay.merchant.repository.ApiKeyRepository;
import com.ritik.razorpay.merchant.repository.MerchantRepository;
import com.ritik.razorpay.merchant.service.ApiKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j

public class ApiKeyServiceImpl implements ApiKeyService {
    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;

    private final ApiKeyMapper apiKeyMapper;
    @Override
    @Transactional
    public ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(()->new ResourceNotFoundException("merchant",merchantId));
        String keyId="rzp_"+request.environment().name().toLowerCase()+ RandomizerUtil.randomBase64(24);
        String rawSecret= RandomizerUtil.randomBase64(40);


        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret)
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(),keyId,rawSecret,request.environment());
    }

    @Override
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {
        return apiKeyMapper.toResponseList(apiKeyRepository.findByMerchant_Id(merchantId));


    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey key= apiKeyRepository.findById(keyId)
                .filter(k ->k.getMerchant().getId().equals(merchantId))
                .orElseThrow(()->new ResourceNotFoundException("Apikey",keyId));

        key.setEnabled(false);
        apiKeyRepository.save(key);

    }

    @Override
    @Transactional
    public @Nullable ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId) {
        ApiKey apiKey= apiKeyRepository.findById(keyId)
                .filter(k ->k.getMerchant().getId().equals(merchantId))
                .orElseThrow(()->new ResourceNotFoundException("Apikey",keyId));
        if(!apiKey.isEnabled()){
            throw new RuntimeException("cannot rotate disable key");
        }

        String newRawSecret= RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret);
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiresAt(LocalDateTime.now().plusHours(24));
        apiKey= apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(),apiKey.getKeyId(),newRawSecret,apiKey.getEnvironment());



    }


}
