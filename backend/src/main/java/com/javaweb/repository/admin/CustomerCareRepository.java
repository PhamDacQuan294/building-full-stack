package com.javaweb.repository.admin;

import com.javaweb.entity.CustomerCareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCareRepository extends JpaRepository<CustomerCareEntity, Long> {
  List<CustomerCareEntity> findByCustomer_IdOrderByCareDateDesc(Long customerId);
}