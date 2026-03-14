package com.javaweb.api.admin;

import com.javaweb.model.request.role.RoleRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.role.PermissionResponseDTO;
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
    ResponseDTO<List<RoleResponseDTO>> response = new ResponseDTO<>();
    response.setData(roleService.getRoles());
    response.setMessage("success");
    response.setDetail("Lấy danh sách nhóm quyền thành công");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDTO<RoleResponseDTO>> getRoleDetail(@PathVariable Long id) {
    ResponseDTO<RoleResponseDTO> response = new ResponseDTO<>();
    response.setData(roleService.getRoleDetail(id));
    response.setMessage("success");
    response.setDetail("Lấy chi tiết nhóm quyền thành công");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/create")
  public ResponseEntity<ResponseDTO<?>> createRole(@RequestBody RoleRequestDTO request) {
    roleService.createRole(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setData(null);
    response.setMessage("success");
    response.setDetail("Thêm nhóm quyền thành công");

    return ResponseEntity.ok(response);
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<ResponseDTO<?>> updateRole(@PathVariable Long id, @RequestBody RoleRequestDTO request) {
    roleService.updateRole(id, request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setData(null);
    response.setMessage("success");
    response.setDetail("Cập nhật nhóm quyền thành công");

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ResponseDTO<?>> deleteRole(@PathVariable Long id) {
    roleService.deleteRole(id);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setData(null);
    response.setMessage("success");
    response.setDetail("Xoá nhóm quyền thành công");

    return ResponseEntity.ok(response);
  }

  @GetMapping("/permissions")
  public ResponseEntity<ResponseDTO<List<PermissionResponseDTO>>> getPermissions() {
    ResponseDTO<List<PermissionResponseDTO>> response = new ResponseDTO<>();
    response.setData(roleService.getPermissions());
    response.setMessage("success");
    response.setDetail("Lấy danh sách quyền thao tác thành công");
    return ResponseEntity.ok(response);
  }
}