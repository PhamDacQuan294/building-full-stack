package com.javaweb.api.client;

import com.javaweb.model.client.request.auth.*;
import com.javaweb.model.client.response.auth.ClientLoginResponseDTO;
import com.javaweb.model.client.response.auth.ClientProfileDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.client.ClientAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/auth")
public class ClientAuthAPI {

  @Autowired
  private ClientAuthService clientAuthService;

  @PostMapping("/register")
  public ResponseDTO<?> register(@RequestBody ClientRegisterRequestDTO request) {
    ClientProfileDTO data = clientAuthService.register(request);

    ResponseDTO<ClientProfileDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Đăng ký thành công");
    response.setData(data);

    return response;
  }

  @PostMapping("/login")
  public ResponseDTO<?> login(@RequestBody ClientLoginRequestDTO request) {
    ClientLoginResponseDTO data = clientAuthService.login(request);

    ResponseDTO<ClientLoginResponseDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Đăng nhập thành công");
    response.setData(data);

    return response;
  }

  @GetMapping("/me")
  public ResponseDTO<?> getProfile(Authentication authentication) {
    ClientProfileDTO data = clientAuthService.getMyProfile(authentication.getName());

    ResponseDTO<ClientProfileDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Lấy hồ sơ thành công");
    response.setData(data);

    return response;
  }

  @PutMapping("/me")
  public ResponseDTO<?> updateProfile(
    Authentication authentication,
    @RequestBody ClientUpdateProfileRequestDTO request
  ) {
    ClientProfileDTO data = clientAuthService.updateProfile(authentication.getName(), request);

    ResponseDTO<ClientProfileDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Cập nhật hồ sơ thành công");
    response.setData(data);

    return response;
  }

  @PutMapping("/change-password")
  public ResponseDTO<?> changePassword(
    Authentication authentication,
    @RequestBody ClientChangePasswordRequestDTO request
  ) {
    clientAuthService.changePassword(authentication.getName(), request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Đổi mật khẩu thành công");
    response.setData(null);

    return response;
  }

  @PostMapping("/forgot-password")
  public ResponseDTO<?> forgotPassword(@RequestBody ClientForgotPasswordRequestDTO request) {
    clientAuthService.forgotPassword(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("OTP đã được gửi");
    response.setData(null);

    return response;
  }

  @PostMapping("/reset-password")
  public ResponseDTO<?> resetPassword(@RequestBody ClientResetPasswordRequestDTO request) {
    clientAuthService.resetPassword(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Đặt lại mật khẩu thành công");
    response.setData(null);

    return response;
  }
}