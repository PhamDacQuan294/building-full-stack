package com.javaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "forgot_password")
public class ForgotPasswordEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "otp", nullable = false)
  private String otp;

  @Column(name = "expired_at", nullable = false)
  private LocalDateTime expiredAt;
}