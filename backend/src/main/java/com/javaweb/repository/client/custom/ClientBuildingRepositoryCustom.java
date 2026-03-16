package com.javaweb.repository.client.custom;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.client.request.building.ClientBuildingSearchRequestDTO;
import com.javaweb.model.client.request.home.HomeSearchRequestDTO;
import com.javaweb.model.client.response.building.HighlightDistrictDTO;

import java.util.List;
import java.util.Optional;

public interface ClientBuildingRepositoryCustom {
  List<BuildingEntity> findFeaturedBuildings();
  List<BuildingEntity> findNewestBuildings();
  List<BuildingEntity> searchBuildings(HomeSearchRequestDTO request);
  long countSearchBuildings(HomeSearchRequestDTO request);
  List<HighlightDistrictDTO> getHighlightDistricts();
  List<String> getAvailableDistricts();

  List<BuildingEntity> findClientBuildings(ClientBuildingSearchRequestDTO request);
  long countClientBuildings(ClientBuildingSearchRequestDTO request);
  Optional<BuildingEntity> findClientBuildingDetail(Long id);
}