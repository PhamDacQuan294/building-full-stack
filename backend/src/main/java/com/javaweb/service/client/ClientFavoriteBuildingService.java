package com.javaweb.service.client;

import com.javaweb.model.client.response.favorite.ClientFavoriteBuildingDTO;
import com.javaweb.model.client.response.favorite.ClientFavoriteToggleResponseDTO;

import java.util.List;

public interface ClientFavoriteBuildingService {
  ClientFavoriteToggleResponseDTO addFavorite(String email, Long buildingId);
  ClientFavoriteToggleResponseDTO removeFavorite(String email, Long buildingId);
  ClientFavoriteToggleResponseDTO checkFavorite(String email, Long buildingId);
  List<ClientFavoriteBuildingDTO> getMyFavorites(String email);
}