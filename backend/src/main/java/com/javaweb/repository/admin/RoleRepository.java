package com.javaweb.repository.admin;

import com.javaweb.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByCode(String code);
  boolean existsByCode(String code);
  boolean existsByCodeAndIdNot(String code, Long id);
  List<RoleEntity> findByIdIn(List<Long> ids);
}