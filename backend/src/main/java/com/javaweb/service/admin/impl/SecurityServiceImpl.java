package com.javaweb.service.admin.impl;

import com.javaweb.entity.UserEntity;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserEntity getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new RuntimeException("Không tìm thấy user đang đăng nhập");
    }

    String email = authentication.getName();

    return userRepository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
  }
}