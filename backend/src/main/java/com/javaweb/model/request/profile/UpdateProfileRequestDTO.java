package com.javaweb.model.request.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDTO {
  private String fullName;
  private String email;
  private String phone;
  private String avatar;
}