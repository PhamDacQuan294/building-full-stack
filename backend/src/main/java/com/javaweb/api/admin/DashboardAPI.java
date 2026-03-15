package com.javaweb.api.admin;

import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.dashboard.DashboardResponseDTO;
import com.javaweb.service.admin.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardAPI {

  @Autowired
  private DashboardService dashboardService;

  @GetMapping("/api/admin/dashboard")
  public ResponseDTO<?> getDashboard() {
    DashboardResponseDTO data = dashboardService.getDashboardData();

    ResponseDTO<DashboardResponseDTO> response = new ResponseDTO<>();
    response.setData(data);
    response.setMessage("success");
    response.setDetail("Lấy dữ liệu dashboard thành công");

    return response;
  }
}