package com.javaweb.model.response.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetailResponseDTO {
  private Long id;
  private String fullName;
  private String phone;
  private String email;
  private String companyName;
  private String demand;
  private String status;
  private String note;
}