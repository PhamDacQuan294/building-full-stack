package com.javaweb.repository.admin.custom;

import com.javaweb.entity.UserEntity;
import com.javaweb.model.request.user.UserSearchRequestDTO;

import java.util.List;

public interface UserRepositoryCustom {
  List<UserEntity> findAll(UserSearchRequestDTO request);
  long countTotalItems(UserSearchRequestDTO request);
}