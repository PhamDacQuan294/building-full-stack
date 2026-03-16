package com.javaweb.api.admin;

import com.javaweb.model.request.contact.AdminContactRequestUpdateStatusDTO;
import com.javaweb.model.response.contact.AdminContactRequestListItemDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.admin.AdminContactRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/contact-requests")
public class AdminContactRequestAPI {

  @Autowired
  private AdminContactRequestService adminContactRequestService;

  @GetMapping
  public ResponseDTO<?> findAll() {
    List<AdminContactRequestListItemDTO> data = adminContactRequestService.findAll();

    ResponseDTO<List<AdminContactRequestListItemDTO>> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Lấy danh sách yêu cầu liên hệ thành công");
    response.setData(data);

    return response;
  }

  @GetMapping("/{id}")
  public ResponseDTO<?> findById(@PathVariable Long id) {
    AdminContactRequestListItemDTO data = adminContactRequestService.findById(id);

    ResponseDTO<AdminContactRequestListItemDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Lấy chi tiết yêu cầu liên hệ thành công");
    response.setData(data);

    return response;
  }

  @PutMapping("/{id}/status")
  public ResponseDTO<?> updateStatus(
    @PathVariable Long id,
    @RequestBody AdminContactRequestUpdateStatusDTO request
  ) {
    adminContactRequestService.updateStatus(id, request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Cập nhật trạng thái thành công");
    response.setData(null);

    return response;
  }
}