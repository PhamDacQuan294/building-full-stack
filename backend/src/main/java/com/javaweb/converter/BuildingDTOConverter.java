package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
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

    String districtName = entity.getDistrict() != null ? entity.getDistrict().getDistrictName() : "";
    buildingResponseDTO.setAddress(entity.getStreet() + "," + entity.getWard() + "," +  districtName);

    Set<RentAreaEntity> rentAreas = entity.getRentAreas();
    String areaResult = rentAreas.stream().map(it-> it.toString()).collect(Collectors.joining(","));
    buildingResponseDTO.setRentArea(areaResult);
    return buildingResponseDTO;
  }
}
