package com.javaweb.service.admin;

import com.javaweb.entity.UserEntity;
import com.javaweb.model.request.notification.EmailLogSearchRequestDTO;
import com.javaweb.model.response.notification.EmailLogResponseDTO;

import java.util.List;

public interface EmailLogService {
  void save(
    UserEntity actor,
    UserEntity receiver,
    String toEmail,
    String subject,
    String content,
    String mailType,
    String module,
    Long objectId,
    boolean sentSuccess,
    String errorMessage
  );

  List<EmailLogResponseDTO> search(EmailLogSearchRequestDTO request);

  long count(EmailLogSearchRequestDTO request);
}