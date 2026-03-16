package com.javaweb.service.client.impl;

import com.javaweb.components.JwtTokenUtil;
import com.javaweb.converter.client.ClientAuthConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.enums.CustomerStatus;
import com.javaweb.model.client.request.auth.ClientChangePasswordRequestDTO;
import com.javaweb.model.client.request.auth.ClientForgotPasswordRequestDTO;
import com.javaweb.model.client.request.auth.ClientLoginRequestDTO;
import com.javaweb.model.client.request.auth.ClientRegisterRequestDTO;
import com.javaweb.model.client.request.auth.ClientResetPasswordRequestDTO;
import com.javaweb.model.client.request.auth.ClientUpdateProfileRequestDTO;
import com.javaweb.model.client.response.auth.ClientLoginResponseDTO;
import com.javaweb.model.client.response.auth.ClientProfileDTO;
import com.javaweb.repository.client.ClientCustomerRepository;
import com.javaweb.service.client.ClientAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClientAuthServiceImpl implements ClientAuthService {

  @Autowired
  private ClientCustomerRepository customerRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private ClientAuthConverter clientAuthConverter;

  private final Map<String, String> forgotPasswordOtpStore = new ConcurrentHashMap<>();

  @Override
  public ClientProfileDTO register(ClientRegisterRequestDTO request) {
    validateRegister(request);

    CustomerEntity entity = new CustomerEntity();
    entity.setFullname(request.getFullName().trim());
    entity.setEmail(request.getEmail().trim());
    entity.setPhone(request.getPhone().trim());
    entity.setPassword(passwordEncoder.encode(request.getPassword()));
    entity.setCustomerStatus(CustomerStatus.ACTIVE);

    CustomerEntity saved = customerRepository.save(entity);
    return clientAuthConverter.toProfileDTO(saved);
  }

  @Override
  public ClientLoginResponseDTO login(ClientLoginRequestDTO request) {
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new RuntimeException("Email không được để trống");
    }

    if (request.getPassword() == null || request.getPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu không được để trống");
    }

    CustomerEntity customer = customerRepository.findByEmailAndDeletedFalse(request.getEmail().trim())
      .orElseThrow(() -> new RuntimeException("Email hoặc mật khẩu không đúng"));

    if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
      throw new RuntimeException("Email hoặc mật khẩu không đúng");
    }

    String token = jwtTokenUtil.generateCustomerToken(customer);

    ClientLoginResponseDTO response = new ClientLoginResponseDTO();
    response.setToken(token);
    response.setUser(clientAuthConverter.toProfileDTO(customer));

    return response;
  }

  @Override
  public ClientProfileDTO getMyProfile(String email) {
    CustomerEntity customer = customerRepository.findByEmailAndDeletedFalse(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    return clientAuthConverter.toProfileDTO(customer);
  }

  @Override
  public ClientProfileDTO updateProfile(String email, ClientUpdateProfileRequestDTO request) {
    CustomerEntity customer = customerRepository.findByEmailAndDeletedFalse(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    if (request.getFullName() != null && !request.getFullName().isBlank()) {
      customer.setFullname(request.getFullName().trim());
    }

    if (request.getPhone() != null && !request.getPhone().isBlank()) {
      String newPhone = request.getPhone().trim();
      if (!newPhone.equals(customer.getPhone())
        && customerRepository.existsByPhoneAndDeletedFalse(newPhone)) {
        throw new RuntimeException("Số điện thoại đã tồn tại");
      }
      customer.setPhone(newPhone);
    }

    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      String newEmail = request.getEmail().trim();
      if (!newEmail.equalsIgnoreCase(customer.getEmail())
        && customerRepository.existsByEmailAndDeletedFalse(newEmail)) {
        throw new RuntimeException("Email đã tồn tại");
      }
      customer.setEmail(newEmail);
    }

    CustomerEntity saved = customerRepository.save(customer);
    return clientAuthConverter.toProfileDTO(saved);
  }

  @Override
  public void changePassword(String email, ClientChangePasswordRequestDTO request) {
    CustomerEntity customer = customerRepository.findByEmailAndDeletedFalse(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    if (request.getOldPassword() == null || request.getOldPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu cũ không được để trống");
    }

    if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu mới không được để trống");
    }

    if (!passwordEncoder.matches(request.getOldPassword(), customer.getPassword())) {
      throw new RuntimeException("Mật khẩu cũ không đúng");
    }

    customer.setPassword(passwordEncoder.encode(request.getNewPassword()));
    customerRepository.save(customer);
  }

  @Override
  public void forgotPassword(ClientForgotPasswordRequestDTO request) {
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new RuntimeException("Email không được để trống");
    }

    CustomerEntity customer = customerRepository.findByEmailAndDeletedFalse(request.getEmail().trim())
      .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

    String otp = String.valueOf(100000 + new Random().nextInt(900000));
    forgotPasswordOtpStore.put(customer.getEmail(), otp);

    System.out.println("OTP reset password for " + customer.getEmail() + ": " + otp);
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

    CustomerEntity customer = customerRepository.findByEmailAndDeletedFalse(request.getEmail().trim())
      .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

    String savedOtp = forgotPasswordOtpStore.get(customer.getEmail());
    if (savedOtp == null || !savedOtp.equals(request.getOtp().trim())) {
      throw new RuntimeException("OTP không hợp lệ");
    }

    customer.setPassword(passwordEncoder.encode(request.getNewPassword()));
    customerRepository.save(customer);

    forgotPasswordOtpStore.remove(customer.getEmail());
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

    if (customerRepository.existsByEmailAndDeletedFalse(request.getEmail().trim())) {
      throw new RuntimeException("Email đã tồn tại");
    }

    if (customerRepository.existsByPhoneAndDeletedFalse(request.getPhone().trim())) {
      throw new RuntimeException("Số điện thoại đã tồn tại");
    }
  }
}