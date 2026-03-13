package com.javaweb.repository.admin;

import com.javaweb.entity.ForgotPasswordEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Long> {
  Optional<ForgotPasswordEntity> findByEmailAndOtp(String email, String otp);

  @Transactional
  @Modifying
  void deleteByEmail(String email);
}