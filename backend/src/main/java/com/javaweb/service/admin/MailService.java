package com.javaweb.service.admin;

import com.javaweb.model.request.notification.AssignmentMailRequestDTO;
import com.javaweb.model.request.notification.NewUserMailRequestDTO;
import com.javaweb.model.request.notification.ResetPasswordMailRequestDTO;

public interface MailService {
  void sendNewUserMail(NewUserMailRequestDTO request);
  void sendResetPasswordOtpMail(ResetPasswordMailRequestDTO request);
  void sendAssignmentMail(AssignmentMailRequestDTO request);
}