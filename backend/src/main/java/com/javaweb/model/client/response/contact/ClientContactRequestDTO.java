package com.javaweb.model.client.response.contact;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientContactRequestDTO {
  private Long id;
  private String fullName;
  private String phone;
  private String email;
  private String message;
  private String status;
  private Long buildingId;
  private String buildingName;
}