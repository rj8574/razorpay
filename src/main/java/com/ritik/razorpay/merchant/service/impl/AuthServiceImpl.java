package com.ritik.razorpay.merchant.service.impl;

import com.ritik.razorpay.common.enums.MerchantStaus;
import com.ritik.razorpay.common.enums.UserRole;
import com.ritik.razorpay.common.exception.DuplicateResourceException;
import com.ritik.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.ritik.razorpay.merchant.dto.response.MerchantResponse;
import com.ritik.razorpay.merchant.entity.AppUser;
import com.ritik.razorpay.merchant.entity.Merchant;
import com.ritik.razorpay.merchant.repository.AppUserRepository;
import com.ritik.razorpay.merchant.repository.MerchantRepository;
import com.ritik.razorpay.merchant.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;
    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {
        if(merchantRepository.existsByEmail(request.email()))
        {
            throw new DuplicateResourceException("DUPLICATE_MRECHANT_EMAIL",
                    "Merchant withEmail already exists "+request.email());
        }
        Merchant merchant = Merchant.builder()
                .businessName(request.businessName())
                .businessType(request.businessType())
                .name(request.name())
                .email(request.email())
                .status(MerchantStaus.PENDING_KYC)
                .build();
        merchant=merchantRepository.save(merchant);

        AppUser appUser= AppUser.builder()
                .email(request.name())
                .merchant(merchant)
                .passwordHash(request.password())
                .role(UserRole.OWNER)
                .build();
        appUser=appUserRepository.save(appUser);




        return new MerchantResponse(merchant.getId(), merchant.getName(),
                merchant.getEmail(),merchant.getBusinessName(),
                merchant.getBusinessType(), merchant.getStatus());
    }
}
