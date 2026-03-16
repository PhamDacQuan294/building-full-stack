package com.javaweb.converter.client;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.FavoriteBuildingEntity;
import com.javaweb.enums.RentType;
import com.javaweb.model.client.response.favorite.ClientFavoriteBuildingDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClientFavoriteBuildingConverter {

  @Autowired
  private ModelMapper modelMapper;

  public ClientFavoriteBuildingDTO toDTO(FavoriteBuildingEntity entity) {
    BuildingEntity building = entity.getBuilding();

    ClientFavoriteBuildingDTO dto = modelMapper.map(building, ClientFavoriteBuildingDTO.class);
    dto.setFavoriteId(entity.getId());
    dto.setBuildingId(building.getId());
    dto.setImageUrl(building.getImage());
    dto.setRentPrice(building.getRentPrice());
    dto.setFloorArea(building.getFloorArea());
    dto.setDistrict(building.getDistrict() != null ? building.getDistrict().name() : null);
    dto.setRentType(formatRentTypes(building));
    dto.setAddress(buildAddress(building));
    dto.setStatus(building.getStatus() != null ? building.getStatus().name() : null);

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