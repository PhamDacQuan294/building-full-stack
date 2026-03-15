package com.javaweb.model.response.activitylog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityLogResponseDTO {
  private Long id;
  private Long actorId;
  private String actorEmail;
  private String actorName;
  private String action;
  private String module;
  private String description;
  private Long objectId;
  private String createdDate;
}