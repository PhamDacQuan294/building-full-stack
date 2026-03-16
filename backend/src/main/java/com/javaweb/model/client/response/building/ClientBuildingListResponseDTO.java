package com.javaweb.model.client.response.building;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientBuildingListResponseDTO {
  private List<ClientBuildingListItemDTO> items;
  private Long totalItems;
  private Integer page;
  private Integer limit;
  private Integer totalPages;
}