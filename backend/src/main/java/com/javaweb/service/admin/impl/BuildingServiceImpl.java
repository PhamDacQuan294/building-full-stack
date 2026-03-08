package com.javaweb.service.admin.impl;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.model.response.building.BuildingResponseDTO;
import com.javaweb.repository.admin.BuildingRepository;
import com.javaweb.repository.admin.RentAreaRepository;
import com.javaweb.repository.admin.custom.BuildingRepositoryCustom;
import com.javaweb.service.admin.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {
  @Autowired
  private BuildingRepositoryCustom buildingRepositoryCustom;

  @Autowired
  private BuildingDTOConverter buildingDTOConverter;

  @Autowired
  private RentAreaRepository rentAreaRepository;

  @Override
  public List<BuildingResponseDTO> findAll(BuildingSearchRequestDTO params) {
    List<BuildingEntity> buildingEntities = buildingRepositoryCustom.findAll(params);
    List<BuildingResponseDTO> result = new ArrayList<>();
    for (BuildingEntity item : buildingEntities) {
      BuildingResponseDTO buildingResponseDTO = buildingDTOConverter.toBuildingResponseDTO(item);
      result.add(buildingResponseDTO);
    }
    return result;
  }
}
