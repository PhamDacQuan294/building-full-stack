package com.javaweb.model.client.response.home;

import com.javaweb.model.client.response.building.ClientBuildingCardDTO;
import com.javaweb.model.client.response.building.HighlightDistrictDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomePageResponseDTO {
  private List<ClientBuildingCardDTO> featuredBuildings;
  private List<ClientBuildingCardDTO> newestBuildings;
  private List<HighlightDistrictDTO> highlightDistricts;
  private List<String> districts;
  private String bannerTitle;
  private String bannerDescription;
}