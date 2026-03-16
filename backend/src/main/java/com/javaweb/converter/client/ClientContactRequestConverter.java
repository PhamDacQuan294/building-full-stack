package com.javaweb.converter.client;

import com.javaweb.entity.ContactRequestEntity;
import com.javaweb.model.client.response.contact.ClientContactRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientContactRequestConverter {

  @Autowired
  private ModelMapper modelMapper;

  public ClientContactRequestDTO toDTO(ContactRequestEntity entity) {
    ClientContactRequestDTO dto = modelMapper.map(entity, ClientContactRequestDTO.class);

    if (entity.getBuilding() != null) {
      dto.setBuildingId(entity.getBuilding().getId());
      dto.setBuildingName(entity.getBuilding().getName());
    }

    return dto;
  }
}