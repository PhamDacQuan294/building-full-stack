package com.javaweb.repository.admin.custom;

import com.javaweb.model.request.notification.EmailLogSearchRequestDTO;
import com.javaweb.model.response.notification.EmailLogResponseDTO;

import java.util.List;

public interface EmailLogRepositoryCustom {
  List<EmailLogResponseDTO> search(EmailLogSearchRequestDTO request);
  long count(EmailLogSearchRequestDTO request);
}