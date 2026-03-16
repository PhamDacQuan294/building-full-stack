package com.javaweb.converter.client;


import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.client.response.building.ClientBuildingCardDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientBuildingConverter {

  @Autowired
  private ModelMapper modelMapper;

  public ClientBuildingCardDTO toBuildingCardDTO(BuildingEntity entity) {
    ClientBuildingCardDTO dto = modelMapper.map(entity, ClientBuildingCardDTO.class);

    String address = "";
    if (entity.getStreet() != null) {
      address += entity.getStreet();
    }
    if (entity.getWard() != null && !entity.getWard().isBlank()) {
      address += ", " + entity.getWard();
    }
    if (entity.getDistrict() != null) {
      address += ", " + entity.getDistrict();
    }

    dto.setAddress(address);
    dto.setImageUrl(entity.getImage());
    dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : null);

    return dto;
  }
}