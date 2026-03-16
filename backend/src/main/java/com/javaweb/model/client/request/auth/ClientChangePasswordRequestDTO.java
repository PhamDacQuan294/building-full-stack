package com.javaweb.model.client.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientChangePasswordRequestDTO {
  private String oldPassword;
  private String newPassword;
}