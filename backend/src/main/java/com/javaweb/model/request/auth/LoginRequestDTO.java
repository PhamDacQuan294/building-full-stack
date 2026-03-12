package com.javaweb.model.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

  @Email(message = "Email không hợp lệ")
  @NotBlank(message = "Email không được để trống")
  private String email;

  @NotBlank(message = "Password không được để trống")
  private String password;
}
