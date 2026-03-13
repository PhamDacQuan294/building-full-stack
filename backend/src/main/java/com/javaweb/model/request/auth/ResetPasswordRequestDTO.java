package com.javaweb.model.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
  private String email;
  private String otp;
  private String newPassword;
}