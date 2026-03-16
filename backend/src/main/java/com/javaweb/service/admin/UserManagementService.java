package com.javaweb.service.admin;

import com.javaweb.model.request.user.ChangeUserStatusRequestDTO;
import com.javaweb.model.request.user.CreateUserRequestDTO;
import com.javaweb.model.request.user.ResetUserPasswordRequestDTO;
import com.javaweb.model.request.user.UpdateUserRequestDTO;
import com.javaweb.model.request.user.UserSearchRequestDTO;
import com.javaweb.model.response.user.UserDetailResponseDTO;
import com.javaweb.model.response.user.UserResponseDTO;

import java.util.List;

public interface UserManagementService {
  List<UserResponseDTO> findAll(UserSearchRequestDTO request);
  long countTotalItems(UserSearchRequestDTO request);
  void createUser(CreateUserRequestDTO request);
  void updateUser(Long id, UpdateUserRequestDTO request);
  void changeStatus(Long id, ChangeUserStatusRequestDTO request);
  void resetPassword(Long id, ResetUserPasswordRequestDTO request);
  UserDetailResponseDTO getDetail(Long id);
}