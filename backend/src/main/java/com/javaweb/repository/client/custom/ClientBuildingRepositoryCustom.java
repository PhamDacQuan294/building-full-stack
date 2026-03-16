package com.javaweb.repository.client.custom;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.client.request.home.HomeSearchRequestDTO;
import com.javaweb.model.client.response.building.HighlightDistrictDTO;

import java.util.List;

public interface ClientBuildingRepositoryCustom {
  List<BuildingEntity> findFeaturedBuildings();
  List<BuildingEntity> findNewestBuildings();
  List<BuildingEntity> searchBuildings(HomeSearchRequestDTO request);
  long countSearchBuildings(HomeSearchRequestDTO request);
  List<HighlightDistrictDTO> getHighlightDistricts();
  List<String> getAvailableDistricts();
}