package com.javaweb.model.response.role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponseDTO {
  private Long id;
  private String code;
  private String name;
  private String description;
}