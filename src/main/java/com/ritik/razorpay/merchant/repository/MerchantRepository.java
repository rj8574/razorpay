package com.ritik.razorpay.merchant.repository;

import com.ritik.razorpay.merchant.entity.Merchant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    boolean existsByEmail(@NotNull @Size(message = "email is required") String email);
}
