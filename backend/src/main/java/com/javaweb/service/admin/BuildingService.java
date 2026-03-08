package com.javaweb.service.admin;

import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.model.response.building.BuildingResponseDTO;

import java.util.List;

public interface BuildingService {
  List<BuildingResponseDTO> findAll(BuildingSearchRequestDTO params);
}
