package com.javaweb.service.admin;

import com.javaweb.model.request.customer.AssignmentCustomerRequestDTO;
import com.javaweb.model.request.customer.CustomerRequestDTO;
import com.javaweb.model.request.customer.CustomerSearchRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.customer.CustomerDetailResponseDTO;
import com.javaweb.model.response.customer.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
  List<CustomerResponseDTO> findAll(CustomerSearchRequestDTO request);
  int countTotalItems(CustomerSearchRequestDTO request);
  void createCustomer(CustomerRequestDTO request);
  void updateCustomer(Long id, CustomerRequestDTO request);
  void deleteCustomer(Long id);
  CustomerDetailResponseDTO getCustomerDetail(Long id);
  ResponseDTO<?> listStaffs(Long customerId);
  void updateAssignmentCustomer(AssignmentCustomerRequestDTO request);
}