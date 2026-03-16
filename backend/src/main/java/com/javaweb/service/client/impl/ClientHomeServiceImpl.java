package com.javaweb.service.client.impl;

import com.javaweb.converter.client.ClientBuildingConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.client.request.home.HomeSearchRequestDTO;
import com.javaweb.model.client.response.building.ClientBuildingCardDTO;
import com.javaweb.model.client.response.home.HomePageResponseDTO;
import com.javaweb.repository.client.ClientBuildingRepository;
import com.javaweb.service.client.ClientHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientHomeServiceImpl implements ClientHomeService {

  @Autowired
  private ClientBuildingRepository buildingRepository;

  @Autowired
  private ClientBuildingConverter clientBuildingConverter;

  @Override
  public HomePageResponseDTO getHomePageData() {
    HomePageResponseDTO dto = new HomePageResponseDTO();

    List<BuildingEntity> featuredBuildings = buildingRepository.findFeaturedBuildings();
    List<BuildingEntity> newestBuildings = buildingRepository.findNewestBuildings();

    dto.setFeaturedBuildings(toBuildingCardList(featuredBuildings));
    dto.setNewestBuildings(toBuildingCardList(newestBuildings));
    dto.setHighlightDistricts(buildingRepository.getHighlightDistricts());
    dto.setDistricts(buildingRepository.getAvailableDistricts());

    dto.setBannerTitle("Tìm kiếm bất động sản phù hợp với bạn");
    dto.setBannerDescription("Danh sách tòa nhà, mặt bằng và văn phòng nổi bật được cập nhật mới nhất.");

    return dto;
  }

  @Override
  public Map<String, Object> searchBuildings(HomeSearchRequestDTO request) {
    List<BuildingEntity> entities = buildingRepository.searchBuildings(request);
    long totalItems = buildingRepository.countSearchBuildings(request);

    Map<String, Object> result = new HashMap<>();
    result.put("items", toBuildingCardList(entities));
    result.put("totalItems", totalItems);
    result.put("page", request.getPage());
    result.put("limit", request.getLimit());

    return result;
  }

  private List<ClientBuildingCardDTO> toBuildingCardList(List<BuildingEntity> entities) {
    List<ClientBuildingCardDTO> result = new ArrayList<>();

    for (BuildingEntity entity : entities) {
      result.add(clientBuildingConverter.toBuildingCardDTO(entity));
    }

    return result;
  }
}