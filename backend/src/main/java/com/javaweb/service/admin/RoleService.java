package com.javaweb.service.admin;

import com.javaweb.model.request.role.RoleRequestDTO;
import com.javaweb.model.response.role.RoleResponseDTO;

import java.util.List;

public interface RoleService {
  List<RoleResponseDTO> findAll();
  void createRole(RoleRequestDTO request);
  RoleResponseDTO getRoleDetail(Long id);
  void updateRole(Long id, RoleRequestDTO request);
}