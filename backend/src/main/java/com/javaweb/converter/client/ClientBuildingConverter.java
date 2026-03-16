package com.javaweb.converter.client;


import com.javaweb.entity.BuildingEntity;
import com.javaweb.enums.RentType;
import com.javaweb.model.client.response.building.ClientBuildingCardDTO;
import com.javaweb.model.client.response.building.ClientBuildingDetailDTO;
import com.javaweb.model.client.response.building.ClientBuildingListItemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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

  public ClientBuildingListItemDTO toListItemDTO(BuildingEntity entity) {
    ClientBuildingListItemDTO dto = modelMapper.map(entity, ClientBuildingListItemDTO.class);

    dto.setImageUrl(entity.getImage());
    dto.setFloorArea(entity.getFloorArea());
    dto.setRentPrice(entity.getRentPrice());
    dto.setDistrict(entity.getDistrict() != null ? entity.getDistrict().name() : null);
    dto.setRentType(formatRentTypes(entity));
    dto.setAddress(buildAddress(entity));

    return dto;
  }

  public ClientBuildingDetailDTO toDetailDTO(BuildingEntity entity) {
    ClientBuildingDetailDTO dto = modelMapper.map(entity, ClientBuildingDetailDTO.class);

    dto.setImageUrl(entity.getImage());
    dto.setFloorArea(entity.getFloorArea());
    dto.setRentPrice(entity.getRentPrice());
    dto.setDistrict(entity.getDistrict() != null ? entity.getDistrict().name() : null);
    dto.setRentType(formatRentTypes(entity));
    dto.setDescription(entity.getNote());
    dto.setAddress(buildAddress(entity));

    return dto;
  }

  private String buildAddress(BuildingEntity entity) {
    String street = entity.getStreet() != null ? entity.getStreet() : "";
    String ward = entity.getWard() != null ? entity.getWard() : "";
    String district = entity.getDistrict() != null ? entity.getDistrict().name() : "";

    return String.join(", ",
      java.util.stream.Stream.of(street, ward, district)
        .filter(item -> item != null && !item.isBlank())
        .toList()
    );
  }

  private String formatRentTypes(BuildingEntity entity) {
    if (entity.getRentTypes() == null || entity.getRentTypes().isEmpty()) {
      return null;
    }

    return entity.getRentTypes()
      .stream()
      .map(RentType::name)
      .collect(Collectors.joining(", "));
  }
}