package com.javaweb.model.response.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffAssignmentResponseDTO {
  private Long staffId;
  private String fullName;
  private boolean checked;
}