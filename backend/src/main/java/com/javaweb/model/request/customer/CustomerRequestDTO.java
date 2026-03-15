package com.javaweb.model.request.customer;

import com.javaweb.enums.CustomerStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {
  private String fullName;
  private String phone;
  private String email;
  private String companyName;
  private String demand;
  private CustomerStatus status;
  private String note;
}