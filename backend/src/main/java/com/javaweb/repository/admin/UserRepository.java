package com.javaweb.repository.admin;

import com.javaweb.entity.UserEntity;
import com.javaweb.enums.CommonStatus;
import com.javaweb.repository.admin.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {
  List<UserEntity> findByStatusAndRoles_Code(CommonStatus status, String code);
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findByUsername(String username);
  boolean existsByEmail(String email);
  boolean existsByPhone(String phone);
}
