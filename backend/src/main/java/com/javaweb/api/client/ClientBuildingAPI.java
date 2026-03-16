package com.javaweb.api.client;

import com.javaweb.model.client.request.building.ClientBuildingSearchRequestDTO;
import com.javaweb.model.client.response.building.ClientBuildingDetailDTO;
import com.javaweb.model.client.response.building.ClientBuildingListResponseDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.client.ClientBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/buildings")
public class ClientBuildingAPI {

  @Autowired
  private ClientBuildingService clientBuildingService;

  @GetMapping
  public ResponseDTO<?> getBuildings(ClientBuildingSearchRequestDTO request) {
    ClientBuildingListResponseDTO data = clientBuildingService.getBuildings(request);

    ResponseDTO<ClientBuildingListResponseDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Lấy danh sách bất động sản thành công");
    response.setData(data);

    return response;
  }

  @GetMapping("/{id}")
  public ResponseDTO<?> getBuildingDetail(@PathVariable Long id) {
    ClientBuildingDetailDTO data = clientBuildingService.getBuildingDetail(id);

    ResponseDTO<ClientBuildingDetailDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Lấy chi tiết bất động sản thành công");
    response.setData(data);

    return response;
  }
}