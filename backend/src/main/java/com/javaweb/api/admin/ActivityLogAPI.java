package com.javaweb.api.admin;

import com.javaweb.model.request.activitylog.ActivityLogSearchRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.activitylog.ActivityLogResponseDTO;
import com.javaweb.service.admin.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/activity-logs")
public class ActivityLogAPI {

  @Autowired
  private ActivityLogService activityLogService;

  @GetMapping
  public ResponseDTO<?> getLogs(ActivityLogSearchRequestDTO request) {
    List<ActivityLogResponseDTO> logs = activityLogService.search(request);
    long totalItems = activityLogService.count(request);

    ResponseDTO<List<ActivityLogResponseDTO>> response = new ResponseDTO<>();
    response.setData(logs);
    response.setTotalItem(totalItems);
    response.setMessage("success");
    response.setDetail("Lấy danh sách log thành công");

    return response;
  }
}