package com.javaweb.service.admin;

import com.javaweb.model.request.user.CreateUserRequestDTO;
import com.javaweb.model.response.user.UserItemResponseDTO;

import java.util.List;
import java.util.Map;

public interface IUserService {
  Map<Long, String> getStaffs();

  String login(String email, String password) throws Exception;

  List<UserItemResponseDTO> getUsers();
  void createUser(CreateUserRequestDTO request);
}
