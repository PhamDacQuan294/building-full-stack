package com.javaweb.service.admin.impl;

import com.javaweb.converter.AdminContactRequestConverter;
import com.javaweb.entity.ContactRequestEntity;
import com.javaweb.model.request.contact.AdminContactRequestUpdateStatusDTO;
import com.javaweb.model.response.contact.AdminContactRequestListItemDTO;
import com.javaweb.repository.ContactRequestRepository;
import com.javaweb.service.admin.AdminContactRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AdminContactRequestServiceImpl implements AdminContactRequestService {

  @Autowired
  private ContactRequestRepository contactRequestRepository;

  @Autowired
  private AdminContactRequestConverter adminContactRequestConverter;

  @Override
  public List<AdminContactRequestListItemDTO> findAll() {
    List<ContactRequestEntity> entities = contactRequestRepository.findAll();
    entities.sort(Comparator.comparing(ContactRequestEntity::getId).reversed());

    List<AdminContactRequestListItemDTO> result = new ArrayList<>();
    for (ContactRequestEntity entity : entities) {
      result.add(adminContactRequestConverter.toListItemDTO(entity));
    }
    return result;
  }

  @Override
  public AdminContactRequestListItemDTO findById(Long id) {
    ContactRequestEntity entity = contactRequestRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu liên hệ"));

    return adminContactRequestConverter.toListItemDTO(entity);
  }

  @Override
  public void updateStatus(Long id, AdminContactRequestUpdateStatusDTO request) {
    ContactRequestEntity entity = contactRequestRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu liên hệ"));

    if (request.getStatus() == null || request.getStatus().isBlank()) {
      throw new RuntimeException("Trạng thái không hợp lệ");
    }

    entity.setRequestStatus(request.getStatus().trim());
    contactRequestRepository.save(entity);
  }
}