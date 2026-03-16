package com.javaweb.converter;

import com.javaweb.entity.ContactRequestEntity;
import com.javaweb.model.response.contact.AdminContactRequestListItemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class AdminContactRequestConverter {

  @Autowired
  private ModelMapper modelMapper;

  public AdminContactRequestListItemDTO toListItemDTO(ContactRequestEntity entity) {
    AdminContactRequestListItemDTO dto = modelMapper.map(entity, AdminContactRequestListItemDTO.class);

    if (entity.getBuilding() != null) {
      dto.setBuildingId(entity.getBuilding().getId());
      dto.setBuildingName(entity.getBuilding().getName());
    }

    if (entity.getCreatedDate() != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      dto.setCreatedDate(formatter.format(entity.getCreatedDate()));
    }

    return dto;
  }
}