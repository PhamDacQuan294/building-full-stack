package com.javaweb.service.admin;

public interface EmailService {
  void sendOtpEmail(String toEmail, String otp);
}