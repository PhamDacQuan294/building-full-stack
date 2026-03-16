package com.javaweb.service.client.impl;

import com.javaweb.converter.client.ClientBuildingConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.client.request.building.ClientBuildingSearchRequestDTO;
import com.javaweb.model.client.response.building.ClientBuildingDetailDTO;
import com.javaweb.model.client.response.building.ClientBuildingListItemDTO;
import com.javaweb.model.client.response.building.ClientBuildingListResponseDTO;
import com.javaweb.repository.client.ClientBuildingRepository;
import com.javaweb.service.client.ClientBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientBuildingServiceImpl implements ClientBuildingService {

  @Autowired
  private ClientBuildingRepository clientBuildingRepository;

  @Autowired
  private ClientBuildingConverter clientBuildingConverter;

  @Override
  public ClientBuildingListResponseDTO getBuildings(ClientBuildingSearchRequestDTO request) {
    List<BuildingEntity> entities = clientBuildingRepository.findClientBuildings(request);
    long totalItems = clientBuildingRepository.countClientBuildings(request);

    int page = request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage();
    int limit = request.getLimit() == null || request.getLimit() <= 0 ? 8 : request.getLimit();
    int totalPages = (int) Math.ceil((double) totalItems / limit);

    List<ClientBuildingListItemDTO> items = new ArrayList<>();
    for (BuildingEntity entity : entities) {
      items.add(clientBuildingConverter.toListItemDTO(entity));
    }

    ClientBuildingListResponseDTO response = new ClientBuildingListResponseDTO();
    response.setItems(items);
    response.setTotalItems(totalItems);
    response.setPage(page);
    response.setLimit(limit);
    response.setTotalPages(totalPages);

    return response;
  }

  @Override
  public ClientBuildingDetailDTO getBuildingDetail(Long id) {
    BuildingEntity entity = clientBuildingRepository.findClientBuildingDetail(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy bất động sản"));

    return clientBuildingConverter.toDetailDTO(entity);
  }
}