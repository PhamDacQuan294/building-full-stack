package com.javaweb.service.client.impl;

import com.javaweb.converter.client.ClientContactRequestConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.ContactRequestEntity;
import com.javaweb.model.client.request.contact.ClientContactRequestCreateDTO;
import com.javaweb.model.client.response.contact.ClientContactRequestDTO;
import com.javaweb.repository.client.ContactRequestRepository;
import com.javaweb.repository.client.ClientBuildingRepository;
import com.javaweb.service.client.ClientContactRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientContactRequestServiceImpl implements ClientContactRequestService {

  @Autowired
  private ContactRequestRepository contactRequestRepository;

  @Autowired
  private ClientBuildingRepository clientBuildingRepository;

  @Autowired
  private ClientContactRequestConverter clientContactRequestConverter;

  @Override
  public ClientContactRequestDTO create(ClientContactRequestCreateDTO request) {
    validateRequest(request);

    ContactRequestEntity entity = new ContactRequestEntity();
    entity.setFullName(request.getFullName().trim());
    entity.setPhone(request.getPhone().trim());
    entity.setEmail(request.getEmail() != null ? request.getEmail().trim() : null);
    entity.setMessage(request.getMessage() != null ? request.getMessage().trim() : null);
    entity.setRequestStatus("NEW");

    if (request.getBuildingId() != null) {
      BuildingEntity building = clientBuildingRepository.findById(request.getBuildingId())
        .orElseThrow(() -> new RuntimeException("Không tìm thấy bất động sản"));

      entity.setBuilding(building);
    }

    ContactRequestEntity savedEntity = contactRequestRepository.save(entity);
    return clientContactRequestConverter.toDTO(savedEntity);
  }

  private void validateRequest(ClientContactRequestCreateDTO request) {
    if (request.getFullName() == null || request.getFullName().isBlank()) {
      throw new RuntimeException("Họ tên không được để trống");
    }

    if (request.getPhone() == null || request.getPhone().isBlank()) {
      throw new RuntimeException("Số điện thoại không được để trống");
    }
  }
}