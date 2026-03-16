package com.javaweb.model.client.request.contact;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientContactRequestCreateDTO {
  private String fullName;
  private String phone;
  private String email;
  private String message;
  private Long buildingId;
}