package com.javaweb.model.request.customer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignmentCustomerRequestDTO {
  private Long customerId;
  private List<Long> staffs;
}