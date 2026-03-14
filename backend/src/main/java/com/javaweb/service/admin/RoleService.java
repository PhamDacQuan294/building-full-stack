package com.javaweb.service.admin;

import com.javaweb.model.request.role.RoleRequestDTO;
import com.javaweb.model.response.role.PermissionResponseDTO;
import com.javaweb.model.response.role.RoleResponseDTO;

import java.util.List;

public interface RoleService {
  List<RoleResponseDTO> getRoles();
  void createRole(RoleRequestDTO request);
  RoleResponseDTO getRoleDetail(Long id);
  void updateRole(Long id, RoleRequestDTO request);
  void deleteRole(Long id);
  List<PermissionResponseDTO> getPermissions();
}