package com.javaweb.model.request.building;

import java.util.List;

import com.javaweb.enums.CommonStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentBuildingRequestDTO {
	private Long buildingId;
  private List<Long> staffs;
}
