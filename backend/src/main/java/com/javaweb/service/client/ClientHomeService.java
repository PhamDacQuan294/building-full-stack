package com.javaweb.service.client;

import com.javaweb.model.client.request.home.HomeSearchRequestDTO;
import com.javaweb.model.client.response.home.HomePageResponseDTO;

import java.util.List;
import java.util.Map;

public interface ClientHomeService {
  HomePageResponseDTO getHomePageData();
  Map<String, Object> searchBuildings(HomeSearchRequestDTO request);
}