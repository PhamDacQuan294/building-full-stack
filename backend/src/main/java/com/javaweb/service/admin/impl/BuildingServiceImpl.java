package com.javaweb.service.admin.impl;

import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.enums.CommonStatus;
import com.javaweb.model.request.building.AssignmentBuildingRequestDTO;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.model.request.common.ChangeMultiStatusRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.building.BuildingResponseDTO;
import com.javaweb.model.response.user.StaffResponseDTO;
import com.javaweb.repository.admin.AssignmentBuildingRepository;
import com.javaweb.repository.admin.BuildingRepository;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.repository.admin.custom.BuildingRepositoryCustom;
import com.javaweb.service.admin.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingRepositoryCustom buildingRepositoryCustom;

	@Autowired
	private BuildingDTOConverter buildingDTOConverter;

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AssignmentBuildingRepository assignmentBuildingRepository;

	@Override
	public List<BuildingResponseDTO> findAll(BuildingSearchRequestDTO params) {
		List<BuildingEntity> buildingEntities = buildingRepositoryCustom.findAll(params);
		List<BuildingResponseDTO> result = new ArrayList<>();
		for (BuildingEntity item : buildingEntities) {
			BuildingResponseDTO buildingResponseDTO = buildingDTOConverter.toBuildingResponseDTO(item);
			result.add(buildingResponseDTO);
		}
		return result;
	}

	@Override
	public void changeStatus(Long id, String status) {

		BuildingEntity buildingEntity = buildingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy toà nhà"));

		// convert String -> Enum
		CommonStatus newStatus = CommonStatus.valueOf(status.toUpperCase());

		buildingEntity.setStatus(newStatus);

		buildingRepository.save(buildingEntity);
	}

	@Override
	public void changeMultiStatus(ChangeMultiStatusRequestDTO request) {

		List<Long> ids = request.getIds();
		String status = request.getStatus();

		if (ids == null || ids.isEmpty()) {
			throw new RuntimeException("Danh sách id trống");
		}

		CommonStatus newStatus = CommonStatus.valueOf(status.toUpperCase());

		List<BuildingEntity> buildings = buildingRepository.findAllById(ids);

		for (BuildingEntity item : buildings) {
			item.setStatus(newStatus);
		}

		buildingRepository.saveAll(buildings);
	}

	@Override
	public ResponseDTO<?> listStaffs(Long buildingId) {
		BuildingEntity building = buildingRepository.findById(buildingId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy toà nhà"));

		List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(CommonStatus.ACTIVE, "STAFF");

		List<AssignmentBuildingEntity> assignmentBuildings = assignmentBuildingRepository
				.findByBuilding_Id(building.getId());

		List<Long> assignedStaffIds = new ArrayList<>();
		for (AssignmentBuildingEntity item : assignmentBuildings) {
			assignedStaffIds.add(item.getStaff().getId());
		}

		List<StaffResponseDTO> staffResponseDTOs = new ArrayList<>();
		ResponseDTO<List<StaffResponseDTO>> responseDTO = new ResponseDTO<>();

		for (UserEntity it : staffs) {
			StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
			staffResponseDTO.setStaffId(it.getId());
			staffResponseDTO.setFullName(it.getFullname());

			if (assignedStaffIds.contains(it.getId())) {
				staffResponseDTO.setChecked("checked");
			} else {
				staffResponseDTO.setChecked("");
			}

			staffResponseDTOs.add(staffResponseDTO);
		}

		responseDTO.setData(staffResponseDTOs);
		responseDTO.setMessage("success");
		responseDTO.setDetail("Load staffs success");

		return responseDTO;
	}

	@Override
	public void updateAssignmentBuilding(AssignmentBuildingRequestDTO assignmentBuildingDTO) {
		Long buildingId = assignmentBuildingDTO.getBuildingId();
	    List<Long> staffIds = assignmentBuildingDTO.getStaffs();
	    
	    BuildingEntity building = buildingRepository.findById(buildingId)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy toà nhà"));
	    

	    assignmentBuildingRepository.deleteByBuilding_Id(buildingId);

	    if (staffIds == null || staffIds.isEmpty()) {
	        return;
	    }

	    for (Long staffId : staffIds) {
	        UserEntity staff = userRepository.findById(staffId)
	                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với id = " + staffId));
	        
	        AssignmentBuildingEntity assignment = new AssignmentBuildingEntity();
	        assignment.setBuilding(building);
	        assignment.setStaff(staff);

	        assignmentBuildingRepository.save(assignment);
	    }
	}

}
