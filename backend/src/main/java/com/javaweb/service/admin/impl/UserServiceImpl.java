package com.javaweb.service.admin.impl;

import com.javaweb.entity.UserEntity;
import com.javaweb.enums.CommonStatus;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public Map<Long, String> getStaffs() {
    Map<Long, String> listStaffs = new HashMap<>();
    List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(CommonStatus.ACTIVE, "STAFF");
    for (UserEntity it : staffs) {
      listStaffs.put(it.getId(), it.getFullname());
    }
    return listStaffs;
  }
}
