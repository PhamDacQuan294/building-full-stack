package com.javaweb.service.admin.impl;

import com.javaweb.entity.ForgotPasswordEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.repository.admin.ForgotPasswordRepository;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.EmailService;
import com.javaweb.service.admin.ForgotPasswordService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ForgotPasswordRepository forgotPasswordRepository;

  @Autowired
  private EmailService emailService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void sendOtp(String email) {
    UserEntity user = userRepository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

    String otp = generateOtp();

    forgotPasswordRepository.deleteByEmail(email);

    ForgotPasswordEntity forgotPassword = new ForgotPasswordEntity();
    forgotPassword.setEmail(user.getEmail());
    forgotPassword.setOtp(otp);
    forgotPassword.setExpiredAt(LocalDateTime.now().plusMinutes(3));

    forgotPasswordRepository.save(forgotPassword);

    emailService.sendOtpEmail(email, otp);
  }

  @Override
  public void verifyOtp(String email, String otp) {
    ForgotPasswordEntity forgotPassword = forgotPasswordRepository.findByEmailAndOtp(email, otp)
      .orElseThrow(() -> new RuntimeException("OTP không hợp lệ"));

    if (forgotPassword.getExpiredAt().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("OTP đã hết hạn");
    }
  }

  @Override
  public void resetPassword(String email, String otp, String newPassword) {
    ForgotPasswordEntity forgotPassword = forgotPasswordRepository.findByEmailAndOtp(email, otp)
      .orElseThrow(() -> new RuntimeException("OTP không hợp lệ"));

    if (forgotPassword.getExpiredAt().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("OTP đã hết hạn");
    }

    UserEntity user = userRepository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);

    forgotPasswordRepository.deleteByEmail(email);
  }

  private String generateOtp() {
    Random random = new Random();
    int number = 100000 + random.nextInt(900000);
    return String.valueOf(number);
  }
}