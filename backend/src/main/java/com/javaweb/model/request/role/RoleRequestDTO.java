package com.javaweb.model.request.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleRequestDTO {
  private String name;
  private String code;
  private String description;
  private List<Long> permissionIds;
}