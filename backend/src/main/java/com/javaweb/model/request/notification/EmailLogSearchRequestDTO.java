package com.javaweb.model.request.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailLogSearchRequestDTO {
  private String toEmail;
  private String mailType;
  private String module;
  private Integer page = 1;
  private Integer limit = 10;
}