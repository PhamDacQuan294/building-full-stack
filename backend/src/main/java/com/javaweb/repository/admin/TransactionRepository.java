package com.javaweb.repository.admin;

import com.javaweb.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, com.javaweb.repository.admin.custom.TransactionRepositoryCustom {
}