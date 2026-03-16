package com.javaweb.model.client.response.favorite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientFavoriteBuildingDTO {
  private Long favoriteId;
  private Long buildingId;
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