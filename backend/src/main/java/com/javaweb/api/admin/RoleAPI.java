package com.javaweb.api.admin;

import com.javaweb.model.request.role.RoleRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.role.RoleResponseDTO;
import com.javaweb.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
public class RoleAPI {

  @Autowired
  private RoleService roleService;

  @GetMapping("")
  public ResponseEntity<ResponseDTO<List<RoleResponseDTO>>> getRoles() {
    try {
      List<RoleResponseDTO> roles = roleService.findAll();

      ResponseDTO<List<RoleResponseDTO>> response = new ResponseDTO<>();
      response.setData(roles);
      response.setMessage("success");
      response.setDetail("Lấy danh sách nhóm quyền thành công");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      ResponseDTO<List<RoleResponseDTO>> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail(e.getMessage());

      return ResponseEntity.badRequest().body(response);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<ResponseDTO<?>> createRole(@RequestBody RoleRequestDTO request) {
    try {
      roleService.createRole(request);

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("success");
      response.setDetail("Thêm mới nhóm quyền thành công");

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail(e.getMessage());

      return ResponseEntity.badRequest().body(response);
    } catch (Exception e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail("Lỗi server");

      return ResponseEntity.internalServerError().body(response);
    }
  }

  @GetMapping("/detail/{id}")
  public ResponseEntity<ResponseDTO<RoleResponseDTO>> getRoleDetail(@PathVariable Long id) {
    try {
      RoleResponseDTO role = roleService.getRoleDetail(id);

      ResponseDTO<RoleResponseDTO> response = new ResponseDTO<>();
      response.setData(role);
      response.setMessage("success");
      response.setDetail("Lấy chi tiết nhóm quyền thành công");

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<RoleResponseDTO> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail(e.getMessage());

      return ResponseEntity.badRequest().body(response);
    } catch (Exception e) {
      ResponseDTO<RoleResponseDTO> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail("Lỗi server");

      return ResponseEntity.internalServerError().body(response);
    }
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<ResponseDTO<?>> updateRole(@PathVariable Long id, @RequestBody RoleRequestDTO request) {
    try {
      roleService.updateRole(id, request);

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("success");
      response.setDetail("Cập nhật nhóm quyền thành công");

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail(e.getMessage());

      return ResponseEntity.badRequest().body(response);
    } catch (Exception e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail("Lỗi server");

      return ResponseEntity.internalServerError().body(response);
    }
  }
}