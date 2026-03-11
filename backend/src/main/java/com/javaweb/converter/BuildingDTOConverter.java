package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.request.building.BuildingRequestDTO;
import com.javaweb.model.response.building.BuildingDetailResponseDTO;
import com.javaweb.model.response.building.BuildingResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BuildingDTOConverter {
  @Autowired
  private ModelMapper modelMapper;

  public BuildingResponseDTO toBuildingResponseDTO(BuildingEntity entity) {
    BuildingResponseDTO buildingResponseDTO = modelMapper.map(entity, BuildingResponseDTO.class);

    buildingResponseDTO.setImageUrl(entity.getImage());

    String districtName = entity.getDistrict() != null ? entity.getDistrict().getDistrictName() : "";
    buildingResponseDTO.setAddress(entity.getStreet() + "," + entity.getWard() + "," +  districtName);

    Set<RentAreaEntity> rentAreas = entity.getRentAreas();
    String areaResult = rentAreas.stream().map(it-> it.toString()).collect(Collectors.joining(","));
    buildingResponseDTO.setRentArea(areaResult);
    return buildingResponseDTO;
  }

  public BuildingDetailResponseDTO toBuildingDetailResponseDTO(BuildingEntity entity) {
    BuildingDetailResponseDTO dto = modelMapper.map(entity, BuildingDetailResponseDTO.class);
    dto.setImageUrl(entity.getImage());
    if (entity.getDistrict() != null) {
      dto.setDistrict(entity.getDistrict().name());
    }
    return dto;
  }

  public BuildingEntity toBuildingEntity(BuildingRequestDTO request) {
    BuildingEntity building = modelMapper.map(request, BuildingEntity.class);
    return building;
  }

  public void updateBuildingEntity(BuildingRequestDTO request, BuildingEntity building) {
    modelMapper.map(request, building);
  }
}
