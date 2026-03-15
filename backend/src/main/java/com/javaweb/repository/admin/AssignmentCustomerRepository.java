package com.javaweb.repository.admin;

import com.javaweb.entity.AssignmentCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentCustomerRepository extends JpaRepository<AssignmentCustomerEntity, Long> {
  List<AssignmentCustomerEntity> findByCustomer_Id(Long customerId);
  void deleteByCustomer_Id(Long customerId);
}