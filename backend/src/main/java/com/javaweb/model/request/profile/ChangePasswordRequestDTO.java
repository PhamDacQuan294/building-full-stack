package com.javaweb.model.request.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDTO {
  private String oldPassword;
  private String newPassword;
  private String confirmPassword;
}