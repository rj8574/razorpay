package com.ritik.razorpay.merchant.controller;

import com.ritik.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.ritik.razorpay.merchant.dto.response.MerchantResponse;
import com.ritik.razorpay.merchant.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLEngineResult;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<MerchantResponse> signUp(@RequestBody @Valid MerchantSignupRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(
            authService.signup(request)
    );

    }
}
