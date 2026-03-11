package com.javaweb.api.admin;

import com.javaweb.enums.District;
import com.javaweb.enums.RentType;
import com.javaweb.model.request.building.AssignmentBuildingRequestDTO;
import com.javaweb.model.request.building.BuildingRequestDTO;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.model.request.common.ChangeMultiStatusRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.building.BuildingDetailResponseDTO;
import com.javaweb.model.response.building.BuildingListResponseDTO;
import com.javaweb.model.response.building.BuildingResponseDTO;
import com.javaweb.service.admin.BuildingService;
import com.javaweb.service.admin.IUserService;
import com.javaweb.service.admin.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/buildings")
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;

	@Autowired
	private IUserService userService;

  @Autowired
  private UploadService uploadService;

	// [GET] /api/buildings
	@GetMapping("")
	public ResponseEntity<ResponseDTO<BuildingListResponseDTO>> buildingList(BuildingSearchRequestDTO params) {
		try {
			List<BuildingResponseDTO> buildings = buildingService.findAll(params);

			System.out.println(params);

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

	// [PATCH] /api/buildings/change-status/{id}/{status}
	@PatchMapping("/change-status/{id}/{status}")
	public ResponseEntity<ResponseDTO<?>> changeStatus(@PathVariable Long id, @PathVariable String status) {
		try {
			buildingService.changeStatus(id, status);

			ResponseDTO<Object> response = new ResponseDTO<>();
			response.setMessage("Success");
			response.setDetail("Update building status successfully");
			response.setData(null);

			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			ResponseDTO<Object> errorResponse = new ResponseDTO<>();
			errorResponse.setMessage("Failed");
			errorResponse.setDetail(e.getMessage());
			errorResponse.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} catch (Exception e) {
			ResponseDTO<Object> errorResponse = new ResponseDTO<>();
			errorResponse.setMessage("Failed");
			errorResponse.setDetail("Internal server error");
			errorResponse.setData(null);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	// [PATCH] /api/buildings/change-multi
	@PatchMapping("/change-multi")
	public ResponseEntity<ResponseDTO<?>> changeMultiStatus(@RequestBody ChangeMultiStatusRequestDTO request) {

		try {

			buildingService.changeMultiStatus(request);

			ResponseDTO<Object> response = new ResponseDTO<>();
			response.setMessage("Success");
			response.setDetail("Cập nhật trạng thái nhiều toà nhà thành công");
			response.setData(null);

			return ResponseEntity.ok(response);

		} catch (Exception e) {

			ResponseDTO<Object> response = new ResponseDTO<>();
			response.setMessage("Failed");
			response.setDetail(e.getMessage());
			response.setData(null);

			return ResponseEntity.badRequest().body(response);
		}
	}

	//[GET] /api/buildings/{id}/staffs
	@GetMapping("/{id}/staffs")
	public ResponseEntity<ResponseDTO<?>> loadStaffs(@PathVariable Long id) {
		try {
			ResponseDTO<?> response = buildingService.listStaffs(id);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			ResponseDTO<Object> errorResponse = new ResponseDTO<>();
			errorResponse.setMessage("failed");
			errorResponse.setDetail(e.getMessage());
			errorResponse.setData(null);
			return ResponseEntity.badRequest().body(errorResponse);
		} catch (Exception e) {
			ResponseDTO<Object> errorResponse = new ResponseDTO<>();
			errorResponse.setMessage("failed");
			errorResponse.setDetail("Lỗi server");
			errorResponse.setData(null);
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}
	
	//[POST] /api/buildings/assignment
	@PostMapping("/assignment")
	public ResponseEntity<ResponseDTO<?>> updateAssignmentBuilding(@RequestBody AssignmentBuildingRequestDTO assignmentBuildingDTO) {
		System.out.println("buildingId = " + assignmentBuildingDTO.getBuildingId());
	    System.out.println("staffs = " + assignmentBuildingDTO.getStaffs());
	    
	    try {
	        buildingService.updateAssignmentBuilding(assignmentBuildingDTO);

	        ResponseDTO<Object> response = new ResponseDTO<>();
	        response.setMessage("success");
	        response.setDetail("Giao toà nhà thành công");
	        response.setData(null);

	        return ResponseEntity.ok(response);
	    } catch (RuntimeException e) {
	    	e.printStackTrace();
	        ResponseDTO<Object> response = new ResponseDTO<>();
	        response.setMessage("failed");
	        response.setDetail(e.getMessage());
	        response.setData(null);

	        return ResponseEntity.badRequest().body(response);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        ResponseDTO<Object> response = new ResponseDTO<>();
	        response.setMessage("failed");
	        response.setDetail("Lỗi server");
	        response.setData(null);

	        return ResponseEntity.internalServerError().body(response);
	    }
	}

  @PostMapping(value = "/upload", consumes = "multipart/form-data")
  public ResponseEntity<ResponseDTO<?>> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      System.out.println(file);
      String imageUrl = uploadService.uploadFile(file);

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("success");
      response.setDetail("Upload ảnh thành công");
      response.setData(imageUrl);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail("Upload ảnh thất bại");
      response.setData(null);

      return ResponseEntity.badRequest().body(response);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<ResponseDTO<?>> createBuilding(@RequestBody BuildingRequestDTO request) {
    try {
      buildingService.createBuilding(request);

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("success");
      response.setDetail("Tạo toà nhà thành công");
      response.setData(null);

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail(e.getMessage());
      response.setData(null);

      return ResponseEntity.badRequest().body(response);
    } catch (Exception e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail("Lỗi server");
      response.setData(null);

      return ResponseEntity.internalServerError().body(response);
    }
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<ResponseDTO<?>> updateBuilding(@PathVariable Long id, @RequestBody BuildingRequestDTO request) {
    try {
      buildingService.updateBuilding(id, request);

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("success");
      response.setDetail("Cập nhật toà nhà thành công");
      response.setData(null);

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail(e.getMessage());
      response.setData(null);

      return ResponseEntity.badRequest().body(response);
    }
  }

  @GetMapping("/detail/{id}")
  public ResponseEntity<ResponseDTO<?>> getBuildingDetail(@PathVariable Long id) {
    try {
      BuildingDetailResponseDTO building = buildingService.getBuildingDetail(id);

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("success");
      response.setDetail("Lấy chi tiết toà nhà thành công");
      response.setData(building);

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail(e.getMessage());
      response.setData(null);

      return ResponseEntity.badRequest().body(response);
    } catch (Exception e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail("Lỗi server");
      response.setData(null);

      return ResponseEntity.internalServerError().body(response);
    }
  }

  @GetMapping("/meta")
  public ResponseEntity<ResponseDTO<?>> getBuildingMeta() {
    try {
      Map<String, Object> payload = new HashMap<>();
      payload.put("districts", District.type());
      payload.put("rentTypes", RentType.type());
      payload.put("staffs", userService.getStaffs());

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("success");
      response.setDetail("Lấy dữ liệu meta thành công");
      response.setData(payload);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setMessage("failed");
      response.setDetail("Lỗi server");
      response.setData(null);

      return ResponseEntity.internalServerError().body(response);
    }
  }
}
