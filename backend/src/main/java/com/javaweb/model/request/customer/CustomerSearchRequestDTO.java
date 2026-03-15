package com.javaweb.model.request.customer;

import com.javaweb.enums.CustomerStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSearchRequestDTO {
  private String fullName;
  private String phone;
  private String email;
  private Long staffId;
  private CustomerStatus status;

  private Integer page = 1;
  private Integer limit = 10;
}