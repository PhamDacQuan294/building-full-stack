package com.javaweb.repository.admin;

import com.javaweb.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>{
}