package com.javaweb.service.admin.impl;

import com.javaweb.entity.UserEntity;
import com.javaweb.model.request.profile.ChangePasswordRequestDTO;
import com.javaweb.model.request.profile.UpdateProfileRequestDTO;
import com.javaweb.model.response.profile.ProfileResponseDTO;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.ActivityLogService;
import com.javaweb.service.admin.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ActivityLogService activityLogService;

  @Override
  public ProfileResponseDTO getMyProfile() {
    UserEntity user = getCurrentUser();

    ProfileResponseDTO dto = new ProfileResponseDTO();
    dto.setId(user.getId());
    dto.setFullName(user.getFullname());
    dto.setEmail(user.getEmail());
    dto.setPhone(user.getPhone());
    dto.setAvatar(user.getAvatar());
    dto.setUsername(user.getUsername());

    return dto;
  }

  @Override
  public void updateMyProfile(UpdateProfileRequestDTO request) {
    UserEntity user = getCurrentUser();

    user.setFullname(request.getFullName());
    user.setEmail(request.getEmail());
    user.setPhone(request.getPhone());
    user.setAvatar(request.getAvatar());

    userRepository.save(user);
  }

  @Override
  public void changePassword(ChangePasswordRequestDTO request) {
    UserEntity user = getCurrentUser();

    if (request.getOldPassword() == null || request.getOldPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu cũ không được để trống");
    }

    if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
      throw new RuntimeException("Mật khẩu mới không được để trống");
    }

    if (!request.getNewPassword().equals(request.getConfirmPassword())) {
      throw new RuntimeException("Xác nhận mật khẩu không khớp");
    }

    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
      throw new RuntimeException("Mật khẩu cũ không đúng");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);

    activityLogService.save(
      "RESET_PASSWORD",
      "PROFILE",
      "Đổi mật khẩu tài khoản",
      user.getId()
    );
  }

  private UserEntity getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new RuntimeException("Không tìm thấy người dùng đang đăng nhập");
    }

    String email = authentication.getName();

    return userRepository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
  }
}