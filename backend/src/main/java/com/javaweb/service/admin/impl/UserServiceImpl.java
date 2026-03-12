package com.javaweb.service.admin.impl;

import com.javaweb.components.JwtTokenUtil;
import com.javaweb.customexceptions.DataNotFoundException;
import com.javaweb.entity.RoleEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.enums.CommonStatus;
import com.javaweb.model.request.user.CreateUserRequestDTO;
import com.javaweb.model.response.user.UserItemResponseDTO;
import com.javaweb.repository.admin.RoleRepository;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Map<Long, String> getStaffs() {
    Map<Long, String> listStaffs = new HashMap<>();
    List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(CommonStatus.ACTIVE, "STAFF");
    for (UserEntity it : staffs) {
      listStaffs.put(it.getId(), it.getFullname());
    }
    return listStaffs;
  }

  @Override
  public String login(String email, String password) throws Exception {
    Optional<UserEntity> optionalUser = userRepository.findByEmail(email);

    if (optionalUser.isEmpty()) {
      throw new DataNotFoundException("Email hoặc password không đúng");
    }

    UserEntity existingUser = optionalUser.get();

    if (!passwordEncoder.matches(password, existingUser.getPassword())) {
      throw new BadCredentialsException("Email hoặc password không đúng");
    }

    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(email, password, existingUser.getAuthorities());

    authenticationManager.authenticate(authenticationToken);

    return jwtTokenUtil.generateToken(existingUser);
  }

  @Override
  public List<UserItemResponseDTO> getUsers() {
    List<UserEntity> users = userRepository.findAll();
    List<UserItemResponseDTO> result = new ArrayList<>();

    for (UserEntity item : users) {
      UserItemResponseDTO dto = new UserItemResponseDTO();
      dto.setId(item.getId());
      dto.setFullName(item.getFullname());
      dto.setEmail(item.getEmail());
      dto.setPhone(item.getPhone());
      dto.setStatus(item.getStatus().name());

      List<String> roleCodes = item.getRoles()
        .stream()
        .map(RoleEntity::getCode)
        .toList();

      dto.setRoles(roleCodes);
      result.add(dto);
    }

    return result;
  }

  @Override
  public void createUser(CreateUserRequestDTO request) {
    if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
      throw new RuntimeException("Họ tên không được để trống");
    }

    if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
      throw new RuntimeException("Email không được để trống");
    }

    if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
      throw new RuntimeException("Số điện thoại không được để trống");
    }

    if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
      throw new RuntimeException("Mật khẩu không được để trống");
    }

    if (userRepository.existsByEmail(request.getEmail().trim())) {
      throw new RuntimeException("Email đã tồn tại");
    }

    if (userRepository.existsByPhone(request.getPhone().trim())) {
      throw new RuntimeException("Số điện thoại đã tồn tại");
    }

    if (request.getUserName() == null || request.getUserName().trim().isEmpty()) {
      throw new RuntimeException("Tên đăng nhập không được để trống");
    }

    UserEntity user = new UserEntity();
    user.setUsername(request.getUserName().trim());
    user.setFullname(request.getFullName().trim());
    user.setEmail(request.getEmail().trim());
    user.setPhone(request.getPhone().trim());
    user.setPassword(passwordEncoder.encode(request.getPassword().trim()));

    if (request.getStatus() != null && request.getStatus().equalsIgnoreCase("INACTIVE")) {
      user.setStatus(CommonStatus.INACTIVE);
    } else {
      user.setStatus(CommonStatus.ACTIVE);
    }

    Set<RoleEntity> roles = new HashSet<>();

    if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
      for (Long roleId : request.getRoleIds()) {
        RoleEntity role = roleRepository.findById(roleId)
          .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm quyền với id = " + roleId));
        roles.add(role);
      }
    }

    user.setRoles(roles);

    userRepository.save(user);
  }
}
