package com.javaweb.model.response.building;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BuildingListResponseDTO {
  private List<BuildingResponseDTO> buildings;
  private Map<Long, String> staffs;
  private Object districts;
  private Object rentTypes;
}
