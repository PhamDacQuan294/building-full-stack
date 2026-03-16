package com.javaweb.model.client.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientLoginRequestDTO {
  private String email;
  private String password;
}