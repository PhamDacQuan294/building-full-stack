package com.javaweb.model.client.response.favorite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientFavoriteToggleResponseDTO {
  private Long buildingId;
  private boolean favorite;
}