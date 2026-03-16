package com.javaweb.service.client.impl;

import com.javaweb.components.JwtTokenUtil;
import com.javaweb.converter.client.ClientAuthConverter;
import com.javaweb.entity.RoleEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.client.request.auth.*;
import com.javaweb.model.client.response.auth.ClientLoginResponseDTO;
import com.javaweb.model.client.response.auth.ClientProfileDTO;
import com.javaweb.repository.client.ClientRoleRepository;
import com.javaweb.repository.client.ClientUserRepository;
import com.javaweb.service.client.ClientAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClientAuthServiceImpl implements ClientAuthService {

  @Autowired
  private ClientUserRepository userRepository;

  @Autowired
  private ClientRoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private ClientAuthConverter clientAuthConverter;

  // demo đơn giản cho đồ án
  private final Map<String, String> forgotPasswordOtpStore = new ConcurrentHashMap<>();

  @Override
  public ClientProfileDTO register(ClientRegisterRequestDTO request) {
    validateRegister(request);

    RoleEntity clientRole = roleRepository.findByCode("CLIENT")
      .orElseThrow(() -> new RuntimeException("Không tìm thấy role CLIENT"));

    UserEntity entity = new UserEntity();
    entity.setFullname(request.getFullName().trim());
    entity.setEmail(request.getEmail().trim());
    entity.setPhone(request.getPhone().trim());
    entity.setPassword(passwordEncoder.encode(request.getPassword()));
    entity.setStatus(null); // sửa theo status base entity nếu cần
    entity.setRoles(new HashSet<>());
    entity.getRoles().add(clientRole);

    UserEntity savedUser = userRepository.save(entity);
    return clientAuthConverter.toProfileDTO(savedUser);
  }

  @Override
  public ClientLoginResponseDTO login(ClientLoginRequestDTO request) {
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new RuntimeException("Email không được để trống");
    }

    if (request.getPassword() == null || request.getPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu không được để trống");
    }

    UserEntity user = userRepository.findByEmailAndDeletedFalse(request.getEmail().trim())
      .orElseThrow(() -> new RuntimeException("Email hoặc mật khẩu không đúng"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Email hoặc mật khẩu không đúng");
    }

    String token = jwtTokenUtil.generateToken(user);

    ClientLoginResponseDTO response = new ClientLoginResponseDTO();
    response.setToken(token);
    response.setUser(clientAuthConverter.toProfileDTO(user));

    return response;
  }

  @Override
  public ClientProfileDTO getMyProfile(String email) {
    UserEntity user = userRepository.findByEmailAndDeletedFalse(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

    return clientAuthConverter.toProfileDTO(user);
  }

  @Override
  public ClientProfileDTO updateProfile(String email, ClientUpdateProfileRequestDTO request) {
    UserEntity user = userRepository.findByEmailAndDeletedFalse(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

    if (request.getFullName() != null && !request.getFullName().isBlank()) {
      user.setFullname(request.getFullName().trim());
    }

    if (request.getPhone() != null && !request.getPhone().isBlank()) {
      user.setPhone(request.getPhone().trim());
    }

    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      if (!request.getEmail().trim().equalsIgnoreCase(user.getEmail())
        && userRepository.existsByEmailAndDeletedFalse(request.getEmail().trim())) {
        throw new RuntimeException("Email đã tồn tại");
      }
      user.setEmail(request.getEmail().trim());
    }

    UserEntity savedUser = userRepository.save(user);
    return clientAuthConverter.toProfileDTO(savedUser);
  }

  @Override
  public void changePassword(String email, ClientChangePasswordRequestDTO request) {
    UserEntity user = userRepository.findByEmailAndDeletedFalse(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

    if (request.getOldPassword() == null || request.getOldPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu cũ không được để trống");
    }

    if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu mới không được để trống");
    }

    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
      throw new RuntimeException("Mật khẩu cũ không đúng");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);
  }

  @Override
  public void forgotPassword(ClientForgotPasswordRequestDTO request) {
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new RuntimeException("Email không được để trống");
    }

    UserEntity user = userRepository.findByEmailAndDeletedFalse(request.getEmail().trim())
      .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

    String otp = String.valueOf(100000 + new Random().nextInt(900000));
    forgotPasswordOtpStore.put(user.getEmail(), otp);

    // đồ án đơn giản: log ra console
    System.out.println("OTP reset password for " + user.getEmail() + ": " + otp);

    // nếu muốn gửi mail thật thì nối JavaMailSender sau
  }

  @Override
  public void resetPassword(ClientResetPasswordRequestDTO request) {
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new RuntimeException("Email không được để trống");
    }

    if (request.getOtp() == null || request.getOtp().isBlank()) {
      throw new RuntimeException("OTP không được để trống");
    }

    if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu mới không được để trống");
    }

    UserEntity user = userRepository.findByEmailAndDeletedFalse(request.getEmail().trim())
      .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

    String savedOtp = forgotPasswordOtpStore.get(user.getEmail());
    if (savedOtp == null || !savedOtp.equals(request.getOtp().trim())) {
      throw new RuntimeException("OTP không hợp lệ");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);

    forgotPasswordOtpStore.remove(user.getEmail());
  }

  private void validateRegister(ClientRegisterRequestDTO request) {
    if (request.getFullName() == null || request.getFullName().isBlank()) {
      throw new RuntimeException("Họ tên không được để trống");
    }

    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new RuntimeException("Email không được để trống");
    }

    if (request.getPhone() == null || request.getPhone().isBlank()) {
      throw new RuntimeException("Số điện thoại không được để trống");
    }

    if (request.getPassword() == null || request.getPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu không được để trống");
    }

    if (userRepository.existsByEmailAndDeletedFalse(request.getEmail().trim())) {
      throw new RuntimeException("Email đã tồn tại");
    }

    if (userRepository.existsByPhoneAndDeletedFalse(request.getPhone().trim())) {
      throw new RuntimeException("Số điện thoại đã tồn tại");
    }
  }
}