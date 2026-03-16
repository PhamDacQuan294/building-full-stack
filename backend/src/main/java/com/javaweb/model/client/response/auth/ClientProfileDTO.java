package com.javaweb.model.client.response.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientProfileDTO {
  private Long id;
  private String fullName;
  private String email;
  private String phone;
  private String role;
}