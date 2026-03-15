package com.javaweb.model.response.customer;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerCareResponseDTO {
  private Long id;
  private String staffName;
  private String content;
  private String statusAfterCare;
  private Date careDate;
}