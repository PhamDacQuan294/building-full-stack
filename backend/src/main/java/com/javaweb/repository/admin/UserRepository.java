package com.javaweb.repository.admin;

import com.javaweb.entity.UserEntity;
import com.javaweb.enums.CommonStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  List<UserEntity> findByStatusAndRoles_Code(CommonStatus status, String code);
}
