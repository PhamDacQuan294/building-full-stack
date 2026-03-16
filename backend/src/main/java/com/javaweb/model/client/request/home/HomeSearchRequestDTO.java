package com.javaweb.model.client.request.home;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeSearchRequestDTO {
  private String keyword;
  private String district;
  private Long rentPriceFrom;
  private Long rentPriceTo;
  private Integer page = 1;
  private Integer limit = 8;
}