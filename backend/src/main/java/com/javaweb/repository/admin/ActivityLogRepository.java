package com.javaweb.repository.admin;

import com.javaweb.entity.ActivityLogEntity;
import com.javaweb.repository.admin.custom.ActivityLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLogEntity, Long>, ActivityLogRepositoryCustom {
}