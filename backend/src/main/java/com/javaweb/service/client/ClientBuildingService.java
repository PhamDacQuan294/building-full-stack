package com.javaweb.service.client;

import com.javaweb.model.client.request.building.ClientBuildingSearchRequestDTO;
import com.javaweb.model.client.response.building.ClientBuildingDetailDTO;
import com.javaweb.model.client.response.building.ClientBuildingListResponseDTO;

public interface ClientBuildingService {
  ClientBuildingListResponseDTO getBuildings(ClientBuildingSearchRequestDTO request);
  ClientBuildingDetailDTO getBuildingDetail(Long id);
}