package com.javaweb.service.admin.impl;

import com.javaweb.entity.PermissionEntity;
import com.javaweb.entity.RoleEntity;
import com.javaweb.model.request.role.RoleRequestDTO;
import com.javaweb.model.response.role.PermissionResponseDTO;
import com.javaweb.model.response.role.RoleResponseDTO;
import com.javaweb.repository.admin.PermissionRepository;
import com.javaweb.repository.admin.RoleRepository;
import com.javaweb.service.admin.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PermissionRepository permissionRepository;

  @Override
  public List<RoleResponseDTO> getRoles() {
    List<RoleEntity> roles = roleRepository.findAll();
    List<RoleResponseDTO> result = new ArrayList<>();

    for (RoleEntity role : roles) {
      result.add(toRoleResponseDTO(role));
    }

    return result;
  }

  @Override
  public RoleResponseDTO getRoleDetail(Long id) {
    RoleEntity role = roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm quyền"));

    return toRoleResponseDTO(role);
  }

  @Override
  public void createRole(RoleRequestDTO request) {
    if (request.getCode() == null || request.getCode().trim().isEmpty()) {
      throw new RuntimeException("Mã nhóm quyền không được để trống");
    }

    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new RuntimeException("Tên nhóm quyền không được để trống");
    }

    if (roleRepository.existsByCode(request.getCode().trim().toUpperCase())) {
      throw new RuntimeException("Mã nhóm quyền đã tồn tại");
    }

    RoleEntity role = new RoleEntity();
    role.setCode(request.getCode().trim().toUpperCase());
    role.setName(request.getName().trim());
    role.setDescription(request.getDescription());

    Set<PermissionEntity> permissions = getPermissionSet(request.getPermissionIds());
    role.setPermissions(permissions);

    roleRepository.save(role);
  }

  @Override
  public void updateRole(Long id, RoleRequestDTO request) {
    RoleEntity role = roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm quyền"));

    if (request.getCode() == null || request.getCode().trim().isEmpty()) {
      throw new RuntimeException("Mã nhóm quyền không được để trống");
    }

    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new RuntimeException("Tên nhóm quyền không được để trống");
    }

    String newCode = request.getCode().trim().toUpperCase();

    if (!role.getCode().equals(newCode) && roleRepository.existsByCode(newCode)) {
      throw new RuntimeException("Mã nhóm quyền đã tồn tại");
    }

    role.setCode(newCode);
    role.setName(request.getName().trim());
    role.setDescription(request.getDescription());

    Set<PermissionEntity> permissions = getPermissionSet(request.getPermissionIds());
    role.setPermissions(permissions);

    roleRepository.save(role);
  }

  @Override
  public void deleteRole(Long id) {
    RoleEntity role = roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm quyền"));

    roleRepository.delete(role);
  }

  @Override
  public List<PermissionResponseDTO> getPermissions() {
    List<PermissionEntity> permissions = permissionRepository.findAllByOrderByNameAsc();
    List<PermissionResponseDTO> result = new ArrayList<>();

    for (PermissionEntity permission : permissions) {
      PermissionResponseDTO dto = new PermissionResponseDTO();
      dto.setId(permission.getId());
      dto.setCode(permission.getCode());
      dto.setName(permission.getName());
      dto.setDescription(permission.getDescription());
      result.add(dto);
    }

    return result;
  }

  private Set<PermissionEntity> getPermissionSet(List<Long> permissionIds) {
    Set<PermissionEntity> permissions = new HashSet<>();

    if (permissionIds == null || permissionIds.isEmpty()) {
      return permissions;
    }

    for (Long permissionId : permissionIds) {
      PermissionEntity permission = permissionRepository.findById(permissionId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền thao tác với id = " + permissionId));
      permissions.add(permission);
    }

    return permissions;
  }

  private RoleResponseDTO toRoleResponseDTO(RoleEntity role) {
    RoleResponseDTO dto = new RoleResponseDTO();
    dto.setId(role.getId());
    dto.setCode(role.getCode());
    dto.setName(role.getName());
    dto.setDescription(role.getDescription());

    List<PermissionResponseDTO> permissionDTOs = new ArrayList<>();

    for (PermissionEntity permission : role.getPermissions()) {
      PermissionResponseDTO permissionDTO = new PermissionResponseDTO();
      permissionDTO.setId(permission.getId());
      permissionDTO.setCode(permission.getCode());
      permissionDTO.setName(permission.getName());
      permissionDTO.setDescription(permission.getDescription());
      permissionDTOs.add(permissionDTO);
    }

    dto.setPermissions(permissionDTOs);

    return dto;
  }
}