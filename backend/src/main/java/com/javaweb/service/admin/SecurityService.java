package com.javaweb.service.admin;

import com.javaweb.entity.UserEntity;

public interface SecurityService {
  UserEntity getCurrentUser();
}