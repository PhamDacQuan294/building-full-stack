package com.javaweb.model.request.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordMailRequestDTO {
  private Long actorId;
  private Long receiverId;
  private String toEmail;
  private String fullName;
  private String otp;
}