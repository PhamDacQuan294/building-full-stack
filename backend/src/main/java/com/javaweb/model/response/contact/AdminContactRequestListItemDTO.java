package com.javaweb.model.response.contact;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminContactRequestListItemDTO {
  private Long id;
  private String fullName;
  private String phone;
  private String email;
  private String status;
  private Long buildingId;
  private String buildingName;
  private String createdDate;
}