package com.javaweb.repository.admin;

import com.javaweb.entity.EmailLogEntity;
import com.javaweb.repository.admin.custom.EmailLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLogEntity, Long>, EmailLogRepositoryCustom {
}