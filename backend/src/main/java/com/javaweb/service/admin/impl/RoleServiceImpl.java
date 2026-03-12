package com.javaweb.service.admin.impl;

import com.javaweb.entity.RoleEntity;
import com.javaweb.model.request.role.RoleRequestDTO;
import com.javaweb.model.response.role.RoleResponseDTO;
import com.javaweb.repository.admin.RoleRepository;
import com.javaweb.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public List<RoleResponseDTO> findAll() {
    List<RoleEntity> roleEntities = roleRepository.findAll();
    List<RoleResponseDTO> result = new ArrayList<>();

    for (RoleEntity item : roleEntities) {
      RoleResponseDTO dto = new RoleResponseDTO();
      dto.setId(item.getId());
      dto.setName(item.getName());
      dto.setCode(item.getCode());
      result.add(dto);
    }

    return result;
  }

  @Override
  public void createRole(RoleRequestDTO request) {
    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new RuntimeException("Tên nhóm quyền không được để trống");
    }

    if (request.getCode() == null || request.getCode().trim().isEmpty()) {
      throw new RuntimeException("Mã nhóm quyền không được để trống");
    }

    String code = request.getCode().trim().toUpperCase();

    if (roleRepository.existsByCode(code)) {
      throw new RuntimeException("Mã nhóm quyền đã tồn tại");
    }

    RoleEntity role = new RoleEntity();
    role.setName(request.getName().trim());
    role.setCode(code);

    roleRepository.save(role);
  }

  @Override
  public RoleResponseDTO getRoleDetail(Long id) {
    RoleEntity role = roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm quyền"));

    RoleResponseDTO dto = new RoleResponseDTO();
    dto.setId(role.getId());
    dto.setName(role.getName());
    dto.setCode(role.getCode());

    return dto;
  }

  @Override
  public void updateRole(Long id, RoleRequestDTO request) {
    RoleEntity role = roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm quyền"));

    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new RuntimeException("Tên nhóm quyền không được để trống");
    }

    if (request.getCode() == null || request.getCode().trim().isEmpty()) {
      throw new RuntimeException("Mã nhóm quyền không được để trống");
    }

    String code = request.getCode().trim().toUpperCase();

    if (roleRepository.existsByCodeAndIdNot(code, id)) {
      throw new RuntimeException("Mã nhóm quyền đã tồn tại");
    }

    role.setName(request.getName().trim());
    role.setCode(code);

    roleRepository.save(role);
  }
}