package com.javaweb.service.admin;

import com.javaweb.model.request.activitylog.ActivityLogSearchRequestDTO;
import com.javaweb.model.response.activitylog.ActivityLogResponseDTO;

import java.util.List;

public interface ActivityLogService {
  void save(String action, String module, String description, Long objectId);
  List<ActivityLogResponseDTO> search(ActivityLogSearchRequestDTO request);
  long count(ActivityLogSearchRequestDTO request);
}