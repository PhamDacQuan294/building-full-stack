package com.javaweb.service.admin;

import com.javaweb.model.request.building.AssignmentBuildingRequestDTO;
import com.javaweb.model.request.building.BuildingRequestDTO;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.model.request.common.ChangeMultiStatusRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.building.BuildingDetailResponseDTO;
import com.javaweb.model.response.building.BuildingResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BuildingService {
  List<BuildingResponseDTO> findAll(BuildingSearchRequestDTO params, Pageable pageable);
  int countTotalItem(BuildingSearchRequestDTO params);

  void changeStatus(Long id, String status);

  void changeMultiStatus(ChangeMultiStatusRequestDTO request);
  
  ResponseDTO<?> listStaffs(Long buildingId);
  
  void updateAssignmentBuilding(AssignmentBuildingRequestDTO assignmentBuildingDTO);

  void createBuilding(BuildingRequestDTO request);

  void updateBuilding(Long id, BuildingRequestDTO request);

  BuildingDetailResponseDTO getBuildingDetail(Long id);

  void deleteBuilding(Long id);
}
