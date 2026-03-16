package com.javaweb.model.client.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUpdateProfileRequestDTO {
  private String fullName;
  private String phone;
  private String email;
}