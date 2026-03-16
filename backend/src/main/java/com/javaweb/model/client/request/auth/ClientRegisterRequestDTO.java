package com.javaweb.model.client.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRegisterRequestDTO {
  private String fullName;
  private String email;
  private String phone;
  private String password;
}