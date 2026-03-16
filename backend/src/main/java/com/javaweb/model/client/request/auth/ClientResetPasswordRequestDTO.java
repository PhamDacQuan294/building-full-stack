package com.javaweb.model.client.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResetPasswordRequestDTO {
  private String email;
  private String otp;
  private String newPassword;
}