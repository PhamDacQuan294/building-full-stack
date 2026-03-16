package com.javaweb.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchRequestDTO {
  private String fullName;
  private String email;
  private String phone;
  private String status;
  private Integer page = 1;
  private Integer limit = 10;
}