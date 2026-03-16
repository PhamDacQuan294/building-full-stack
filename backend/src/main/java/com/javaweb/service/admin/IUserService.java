package com.javaweb.service.admin;

import com.javaweb.model.request.user.CreateUserRequestDTO;
import com.javaweb.model.response.user.UserResponseDTO;

import java.util.List;
import java.util.Map;

public interface IUserService {
  Map<Long, String> getStaffs();

  String login(String email, String password) throws Exception;

  List<UserResponseDTO> getUsers();
  void createUser(CreateUserRequestDTO request);
}
