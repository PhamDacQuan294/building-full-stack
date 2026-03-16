package com.javaweb.converter.client;

import com.javaweb.entity.UserEntity;
import com.javaweb.model.client.response.auth.ClientProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientAuthConverter {

  @Autowired
  private ModelMapper modelMapper;

  public ClientProfileDTO toProfileDTO(UserEntity entity) {
    ClientProfileDTO dto = modelMapper.map(entity, ClientProfileDTO.class);

    dto.setFullName(entity.getFullname());
    dto.setEmail(entity.getEmail());
    dto.setPhone(entity.getPhone());

    if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
      dto.setRole(entity.getRoles().iterator().next().getCode());
    }

    return dto;
  }
}