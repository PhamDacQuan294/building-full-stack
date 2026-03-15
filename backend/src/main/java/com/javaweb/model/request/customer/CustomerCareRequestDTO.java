package com.javaweb.model.request.customer;

import com.javaweb.enums.CustomerStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCareRequestDTO {
  private Long customerId;
  private String content;
  private CustomerStatus statusAfterCare;
}