package com.javaweb.api.admin;

import com.javaweb.model.request.auth.ForgotPasswordRequestDTO;
import com.javaweb.model.request.auth.ResetPasswordRequestDTO;
import com.javaweb.model.request.auth.VerifyOtpRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.admin.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/password")
public class ForgotPasswordAPI {

  @Autowired
  private ForgotPasswordService forgotPasswordService;

  @PostMapping("/forgot")
  public ResponseEntity<ResponseDTO<?>> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
    try {
      forgotPasswordService.sendOtp(request.getEmail());

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("success");
      response.setDetail("Đã gửi OTP về email");
      response.setData(null);

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail(e.getMessage());
      response.setData(null);

      return ResponseEntity.badRequest().body(response);
    }
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<ResponseDTO<?>> verifyOtp(@RequestBody VerifyOtpRequestDTO request) {
    try {
      forgotPasswordService.verifyOtp(request.getEmail(), request.getOtp());

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("success");
      response.setDetail("OTP hợp lệ");
      response.setData(null);

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail(e.getMessage());
      response.setData(null);

      return ResponseEntity.badRequest().body(response);
    }
  }

  @PostMapping("/reset")
  public ResponseEntity<ResponseDTO<?>> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
    try {
      forgotPasswordService.resetPassword(
        request.getEmail(),
        request.getOtp(),
        request.getNewPassword()
      );

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("success");
      response.setDetail("Đổi mật khẩu thành công");
      response.setData(null);

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail(e.getMessage());
      response.setData(null);

      return ResponseEntity.badRequest().body(response);
    }
  }
}