package com.javaweb.model.request.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateUserRequestDTO {
  private String userName;
  private String fullName;
  private String email;
  private String phone;
  private String password;
  private String status;
  private List<Long> roleIds;
}