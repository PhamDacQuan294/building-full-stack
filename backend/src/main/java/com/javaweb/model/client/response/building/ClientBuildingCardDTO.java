package com.javaweb.model.client.response.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientBuildingCardDTO {
  private Long id;
  private String name;
  private String district;
  private String ward;
  private String street;
  private String address;
  private String imageUrl;
  private Long rentPrice;
  private Long floorArea;
  private String status;
}