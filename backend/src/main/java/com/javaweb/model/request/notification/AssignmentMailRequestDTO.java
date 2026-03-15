package com.javaweb.model.request.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentMailRequestDTO {
  private Long actorId;
  private Long receiverId;
  private String toEmail;
  private String staffName;
  private String title;
  private String content;
  private String module;
  private Long objectId;
}