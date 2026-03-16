package com.javaweb.service.admin.impl;

import com.javaweb.converter.UserDTOConverter;
import com.javaweb.entity.RoleEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.enums.CommonStatus;
import com.javaweb.model.request.notification.NewUserMailRequestDTO;
import com.javaweb.model.request.user.ChangeUserStatusRequestDTO;
import com.javaweb.model.request.user.CreateUserRequestDTO;
import com.javaweb.model.request.user.ResetUserPasswordRequestDTO;
import com.javaweb.model.request.user.UpdateUserRequestDTO;
import com.javaweb.model.request.user.UserSearchRequestDTO;
import com.javaweb.model.response.user.UserDetailResponseDTO;
import com.javaweb.model.response.user.UserResponseDTO;
import com.javaweb.repository.admin.RoleRepository;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.ActivityLogService;
import com.javaweb.service.admin.MailService;
import com.javaweb.service.admin.SecurityService;
import com.javaweb.service.admin.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserManagementServiceImpl implements UserManagementService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserDTOConverter userDTOConverter;

  @Autowired
  private ActivityLogService activityLogService;

  @Autowired
  private MailService mailService;

  @Autowired
  private SecurityService securityService;

  @Override
  public List<UserResponseDTO> findAll(UserSearchRequestDTO request) {
    List<UserEntity> users = userRepository.findAll(request);
    List<UserResponseDTO> result = new ArrayList<>();

    for (UserEntity user : users) {
      result.add(userDTOConverter.toUserResponseDTO(user));
    }

    return result;
  }

  @Override
  public long countTotalItems(UserSearchRequestDTO request) {
    return userRepository.countTotalItems(request);
  }

  @Override
  public void createUser(CreateUserRequestDTO request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new RuntimeException("Email đã tồn tại");
    }

    if (userRepository.findByUsername(request.getUserName()).isPresent()) {
      throw new RuntimeException("Username đã tồn tại");
    }

    String rawPassword = request.getPassword();

    UserEntity user = userDTOConverter.toUserEntity(request);
    user.setPassword(passwordEncoder.encode(rawPassword));
    user.setStatus(CommonStatus.ACTIVE);

    if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
      List<RoleEntity> roles = roleRepository.findByIdIn(request.getRoleIds());
      user.setRoles(new HashSet<>(roles));
    }

    UserEntity savedUser = userRepository.save(user);

    activityLogService.save(
      "CREATE",
      "USER",
      "Tạo người dùng mới: " + savedUser.getEmail(),
      savedUser.getId()
    );

    UserEntity currentAdmin = securityService.getCurrentUser();

    String roleName = savedUser.getRoles() != null && !savedUser.getRoles().isEmpty()
      ? savedUser.getRoles().iterator().next().getName()
      : "";

    NewUserMailRequestDTO mailRequest = new NewUserMailRequestDTO();
    mailRequest.setActorId(currentAdmin.getId());
    mailRequest.setReceiverId(savedUser.getId());
    mailRequest.setToEmail(savedUser.getEmail());
    mailRequest.setFullName(savedUser.getFullname());
    mailRequest.setEmail(savedUser.getEmail());
    mailRequest.setPassword(rawPassword);
    mailRequest.setRoleName(roleName);

    mailService.sendNewUserMail(mailRequest);
  }

  @Override
  public void updateUser(Long id, UpdateUserRequestDTO request) {
    UserEntity user = userRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

    userDTOConverter.updateUserEntity(request, user);

    if (request.getRoleIds() != null) {
      List<RoleEntity> roles = roleRepository.findByIdIn(request.getRoleIds());
      user.setRoles(new HashSet<>(roles));
    }

    userRepository.save(user);

    activityLogService.save(
      "UPDATE",
      "USER",
      "Cập nhật người dùng: " + user.getEmail(),
      user.getId()
    );
  }

  @Override
  public void changeStatus(Long id, ChangeUserStatusRequestDTO request) {
    UserEntity user = userRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

    CommonStatus newStatus = CommonStatus.valueOf(request.getStatus().toUpperCase());
    user.setStatus(newStatus);

    userRepository.save(user);

    activityLogService.save(
      "UPDATE",
      "USER",
      "Đổi trạng thái người dùng: " + user.getEmail() + " -> " + newStatus.name(),
      user.getId()
    );
  }

  @Override
  public void resetPassword(Long id, ResetUserPasswordRequestDTO request) {
    UserEntity user = userRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);

    activityLogService.save(
      "RESET_PASSWORD",
      "USER",
      "Admin reset mật khẩu cho user: " + user.getEmail(),
      user.getId()
    );
  }

  @Override
  public UserDetailResponseDTO getDetail(Long id) {
    UserEntity user = userRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

    return userDTOConverter.toUserDetailResponseDTO(user);
  }
}