package com.javaweb.api.client;

import com.javaweb.model.client.response.favorite.ClientFavoriteBuildingDTO;
import com.javaweb.model.client.response.favorite.ClientFavoriteToggleResponseDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.client.ClientFavoriteBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/favorites")
public class ClientFavoriteBuildingAPI {

  @Autowired
  private ClientFavoriteBuildingService clientFavoriteBuildingService;

  @PostMapping("/{buildingId}")
  public ResponseDTO<?> addFavorite(
    Authentication authentication,
    @PathVariable Long buildingId
  ) {
    ClientFavoriteToggleResponseDTO data =
      clientFavoriteBuildingService.addFavorite(authentication.getName(), buildingId);

    ResponseDTO<ClientFavoriteToggleResponseDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Đã lưu bất động sản yêu thích");
    response.setData(data);
    return response;
  }

  @DeleteMapping("/{buildingId}")
  public ResponseDTO<?> removeFavorite(
    Authentication authentication,
    @PathVariable Long buildingId
  ) {
    ClientFavoriteToggleResponseDTO data =
      clientFavoriteBuildingService.removeFavorite(authentication.getName(), buildingId);

    ResponseDTO<ClientFavoriteToggleResponseDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Đã bỏ lưu bất động sản");
    response.setData(data);
    return response;
  }

  @GetMapping("/{buildingId}/check")
  public ResponseDTO<?> checkFavorite(
    Authentication authentication,
    @PathVariable Long buildingId
  ) {
    ClientFavoriteToggleResponseDTO data =
      clientFavoriteBuildingService.checkFavorite(authentication.getName(), buildingId);

    ResponseDTO<ClientFavoriteToggleResponseDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Kiểm tra yêu thích thành công");
    response.setData(data);
    return response;
  }

  @GetMapping
  public ResponseDTO<?> getMyFavorites(Authentication authentication) {
    List<ClientFavoriteBuildingDTO> data =
      clientFavoriteBuildingService.getMyFavorites(authentication.getName());

    ResponseDTO<List<ClientFavoriteBuildingDTO>> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Lấy danh sách yêu thích thành công");
    response.setData(data);
    return response;
  }
}