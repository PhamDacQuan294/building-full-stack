package com.javaweb.model.client.response.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HighlightDistrictDTO {
  private String code;
  private String name;
  private Long totalBuildings;
}