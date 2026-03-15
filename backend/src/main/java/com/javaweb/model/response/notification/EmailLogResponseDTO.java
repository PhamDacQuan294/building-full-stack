package com.javaweb.model.response.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailLogResponseDTO {
  private Long id;

  private Long actorId;
  private String actorEmail;
  private String actorName;

  private Long receiverId;
  private String receiverEmail;
  private String receiverName;

  private String toEmail;
  private String subject;
  private String content;
  private String mailType;
  private String module;
  private Long objectId;
  private Boolean sentSuccess;
  private String errorMessage;
  private String createdDate;
}