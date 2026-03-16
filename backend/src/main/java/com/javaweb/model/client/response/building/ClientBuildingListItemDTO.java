package com.javaweb.model.client.response.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientBuildingListItemDTO {
  private Long id;
  private String name;
  private String address;
  private String district;
  private String ward;
  private String street;
  private String imageUrl;
  private Integer rentPrice;
  private Integer floorArea;
  private String rentType;
  private String status;
}