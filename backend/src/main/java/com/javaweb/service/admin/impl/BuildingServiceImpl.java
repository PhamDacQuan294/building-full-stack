package com.javaweb.service.admin.impl;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.enums.CommonStatus;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.model.request.common.ChangeMultiStatusRequestDTO;
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

  @Autowired
  private BuildingRepository buildingRepository;

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

  @Override
  public void changeStatus(Long id, String status) {

    BuildingEntity buildingEntity = buildingRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy toà nhà"));

    // convert String -> Enum
    CommonStatus newStatus = CommonStatus.valueOf(status.toUpperCase());

    buildingEntity.setStatus(newStatus);

    buildingRepository.save(buildingEntity);
  }

  @Override
  public void changeMultiStatus(ChangeMultiStatusRequestDTO request) {

    List<Long> ids = request.getIds();
    String status = request.getStatus();

    if(ids == null || ids.isEmpty()){
      throw new RuntimeException("Danh sách id trống");
    }

    CommonStatus newStatus = CommonStatus.valueOf(status.toUpperCase());

    List<BuildingEntity> buildings = buildingRepository.findAllById(ids);

    for(BuildingEntity item : buildings){
      item.setStatus(newStatus);
    }

    buildingRepository.saveAll(buildings);
  }
}
