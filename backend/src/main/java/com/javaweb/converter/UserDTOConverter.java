package com.javaweb.converter;

import com.javaweb.entity.RoleEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.request.user.CreateUserRequestDTO;
import com.javaweb.model.request.user.UpdateUserRequestDTO;
import com.javaweb.model.response.user.UserDetailResponseDTO;
import com.javaweb.model.response.user.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOConverter {

  @Autowired
  private ModelMapper modelMapper;

  public UserEntity toUserEntity(CreateUserRequestDTO request) {
    return modelMapper.map(request, UserEntity.class);
  }

  public void updateUserEntity(UpdateUserRequestDTO request, UserEntity user) {
    modelMapper.map(request, user);
  }

  public UserResponseDTO toUserResponseDTO(UserEntity user) {
    UserResponseDTO dto = modelMapper.map(user, UserResponseDTO.class);
    dto.setStatus(user.getStatus() != null ? user.getStatus().name() : null);

    List<String> roleCodes = user.getRoles() != null
      ? user.getRoles().stream().map(RoleEntity::getCode).collect(Collectors.toList())
      : Collections.emptyList();

    dto.setRoles(roleCodes);
    return dto;
  }

  public UserDetailResponseDTO toUserDetailResponseDTO(UserEntity user) {
    UserDetailResponseDTO dto = modelMapper.map(user, UserDetailResponseDTO.class);
    dto.setStatus(user.getStatus() != null ? user.getStatus().name() : null);

    List<Long> roleIds = user.getRoles() != null
      ? user.getRoles().stream().map(RoleEntity::getId).collect(Collectors.toList())
      : Collections.emptyList();

    dto.setRoleIds(roleIds);
    return dto;
  }
}