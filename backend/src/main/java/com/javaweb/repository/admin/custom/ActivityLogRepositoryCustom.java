package com.javaweb.repository.admin.custom;

import com.javaweb.model.request.activitylog.ActivityLogSearchRequestDTO;
import com.javaweb.model.response.activitylog.ActivityLogResponseDTO;

import java.util.List;

public interface ActivityLogRepositoryCustom {
  List<ActivityLogResponseDTO> search(ActivityLogSearchRequestDTO request);
  long count(ActivityLogSearchRequestDTO request);
}