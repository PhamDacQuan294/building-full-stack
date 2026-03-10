package com.javaweb.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.javaweb.entity.AssignmentBuildingEntity;

public interface AssignmentBuildingRepository extends JpaRepository<AssignmentBuildingEntity, Long> {
    List<AssignmentBuildingEntity> findByBuilding_Id(Long buildingId);
    
    @Transactional
    void deleteByBuilding_Id(Long buildingId);
}
