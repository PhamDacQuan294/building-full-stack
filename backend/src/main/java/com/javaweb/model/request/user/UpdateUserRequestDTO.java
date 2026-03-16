package com.javaweb.model.request.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateUserRequestDTO {
  private String fullName;
  private String email;
  private String phone;
  private String username;
  private String avatar;
  private List<Long> roleIds;
}