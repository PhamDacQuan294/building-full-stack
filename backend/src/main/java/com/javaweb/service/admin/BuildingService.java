package com.javaweb.service.admin;

import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.model.request.common.ChangeMultiStatusRequestDTO;
import com.javaweb.model.response.building.BuildingResponseDTO;

import java.util.List;

public interface BuildingService {
  List<BuildingResponseDTO> findAll(BuildingSearchRequestDTO params);

  void changeStatus(Long id, String status);

  void changeMultiStatus(ChangeMultiStatusRequestDTO request);
}
