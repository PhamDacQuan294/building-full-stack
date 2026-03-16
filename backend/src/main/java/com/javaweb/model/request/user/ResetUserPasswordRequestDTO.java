package com.javaweb.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetUserPasswordRequestDTO {
  private String newPassword;
}