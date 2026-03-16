package com.javaweb.model.client.request.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientBuildingSearchRequestDTO {
  private String keyword;
  private String district;
  private Long rentPriceFrom;
  private Long rentPriceTo;
  private Integer areaFrom;
  private Integer areaTo;
  private String rentType;
  private String sortBy; // newest | priceAsc | priceDesc | areaAsc | areaDesc
  private Integer page = 1;
  private Integer limit = 8;
}