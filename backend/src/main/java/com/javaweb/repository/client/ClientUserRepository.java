package com.javaweb.repository.client;

import com.javaweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientUserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmailAndDeletedFalse(String email);
  boolean existsByEmailAndDeletedFalse(String email);
  boolean existsByPhoneAndDeletedFalse(String phone);
}