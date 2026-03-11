package com.javaweb.model.request.building;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javaweb.enums.CommonStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildingSearchRequestDTO {
  private String name;
  private Long floorArea;
  private String district;
  private String ward;
  private String street;
  private Long numberOfBasement;
  private String direction;
  private Long level;
  private Long areaFrom;
  private Long areaTo;
  private Long rentPriceFrom;
  private Long rentPriceTo;
  private Long staffId;
  private List<String> types;
  private CommonStatus status;
  private String sort;

  private int page = 1;
  private int limit = 10;
}