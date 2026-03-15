package com.javaweb.model.request.activitylog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityLogSearchRequestDTO {
  private String actorEmail;
  private String action;
  private String module;
  private Integer page = 1;
  private Integer limit = 10;
}