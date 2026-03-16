package com.javaweb.model.response.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
  private Long id;
  private String fullName;
  private String email;
  private String phone;
  private String username;
  private String avatar;
  private String status;
  private List<String> roles;
}