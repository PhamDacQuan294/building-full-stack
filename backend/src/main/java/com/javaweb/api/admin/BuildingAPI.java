package com.javaweb.api.admin;

import com.javaweb.enums.District;
import com.javaweb.enums.RentType;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.building.BuildingListResponseDTO;
import com.javaweb.model.response.building.BuildingResponseDTO;
import com.javaweb.service.admin.BuildingService;
import com.javaweb.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/buildings")
public class BuildingAPI {

  @Autowired
  private BuildingService buildingService;

  @Autowired
  private IUserService userService;

  // [GET] /api/buildings
  @GetMapping("")
  public ResponseEntity<ResponseDTO<BuildingListResponseDTO>> buildingList(BuildingSearchRequestDTO params) {
    try {
      List<BuildingResponseDTO> buildings = buildingService.findAll(params);

      BuildingListResponseDTO payload = new BuildingListResponseDTO();
      payload.setBuildings(buildings);
      payload.setStaffs(userService.getStaffs());
      payload.setDistricts(District.type());
      payload.setRentTypes(RentType.type()); // nhớ import enum RentType của bạn

      ResponseDTO<BuildingListResponseDTO> response = new ResponseDTO<>();
      response.setData(payload);
      response.setMessage("Success");
      response.setDetail("Get success building lists");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      ResponseDTO<BuildingListResponseDTO> errorResponse = new ResponseDTO<>();
      errorResponse.setMessage("Failed");
      errorResponse.setDetail(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
}
