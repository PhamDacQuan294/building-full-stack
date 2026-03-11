package com.javaweb.model.response.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingDetailResponseDTO {
  private String name;
  private String district;
  private String ward;
  private String street;
  private Integer numberOfBasement;
  private String floorArea;
  private String rentPrice;
  private String status;
  private String imageUrl;
}