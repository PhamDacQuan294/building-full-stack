package com.javaweb.service.admin.impl;

import com.javaweb.entity.EmailLogEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.request.notification.EmailLogSearchRequestDTO;
import com.javaweb.model.response.notification.EmailLogResponseDTO;
import com.javaweb.repository.admin.EmailLogRepository;
import com.javaweb.service.admin.EmailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmailLogServiceImpl implements EmailLogService {

  @Autowired
  private EmailLogRepository emailLogRepository;

  @Override
  public void save(
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
  ) {
    EmailLogEntity entity = new EmailLogEntity();
    entity.setActor(actor);
    entity.setReceiver(receiver);
    entity.setToEmail(toEmail);
    entity.setSubject(subject);
    entity.setContent(content);
    entity.setMailType(mailType);
    entity.setModule(module);
    entity.setObjectId(objectId);
    entity.setSentSuccess(sentSuccess);
    entity.setErrorMessage(errorMessage);
    entity.setCreatedDate(new Date());

    emailLogRepository.save(entity);
  }

  @Override
  public List<EmailLogResponseDTO> search(EmailLogSearchRequestDTO request) {
    return emailLogRepository.search(request);
  }

  @Override
  public long count(EmailLogSearchRequestDTO request) {
    return emailLogRepository.count(request);
  }
}