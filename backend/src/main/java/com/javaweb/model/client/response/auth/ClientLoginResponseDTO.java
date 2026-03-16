package com.javaweb.model.client.response.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientLoginResponseDTO {
  private String token;
  private ClientProfileDTO user;
}