package com.javaweb.repository.admin;

import com.javaweb.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
  List<PermissionEntity> findAllByOrderByNameAsc();
}