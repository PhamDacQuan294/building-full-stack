package com.javaweb.model.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequestDTO {
  private String email;
}