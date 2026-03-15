package com.javaweb.repository.admin.custom;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.request.customer.CustomerSearchRequestDTO;

import java.util.List;

public interface CustomerRepositoryCustom {
  List<CustomerEntity> findAll(CustomerSearchRequestDTO request);
  int countTotalItems(CustomerSearchRequestDTO request);
}