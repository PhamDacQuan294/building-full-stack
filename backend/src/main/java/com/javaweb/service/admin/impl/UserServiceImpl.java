package com.javaweb.service.admin.impl;

import com.javaweb.components.JwtTokenUtil;
import com.javaweb.customexceptions.DataNotFoundException;
import com.javaweb.entity.UserEntity;
import com.javaweb.enums.CommonStatus;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
