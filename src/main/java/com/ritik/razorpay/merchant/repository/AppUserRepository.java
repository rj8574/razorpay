package com.ritik.razorpay.merchant.repository;

import com.ritik.razorpay.merchant.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
}
