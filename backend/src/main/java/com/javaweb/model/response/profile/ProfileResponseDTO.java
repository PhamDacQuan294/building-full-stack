package com.javaweb.model.response.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDTO {
  private Long id;
  private String fullName;
  private String email;
  private String phone;
  private String avatar;
  private String username;
}