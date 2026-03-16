package com.javaweb.repository.client;

import com.javaweb.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientCustomerRepository extends JpaRepository<CustomerEntity, Long> {
  Optional<CustomerEntity> findByEmailAndDeletedFalse(String email);
  boolean existsByEmailAndDeletedFalse(String email);
  boolean existsByPhoneAndDeletedFalse(String phone);
}