package com.javaweb.model.request.building;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty("basement")
  private Long numberOfBasement;

  private String direction;
  private Long level;

  private Long areaFrom;
  private Long areaTo;

  @JsonProperty("rentFrom")
  private Long rentPriceFrom;

  @JsonProperty("rentTo")
  private Long rentPriceTo;

  private Long staffId;

  @JsonProperty("types")
  private List<String> types;
}